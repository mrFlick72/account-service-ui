package it.valeriovaudi.onlyoneportal.accountservice.adapters.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cache.CacheManager;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageResponse;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

import static reactor.core.publisher.Mono.fromCompletionStage;

public class I18nMessagesCacheRefresher implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(I18nMessagesCacheRefresher.class);

    private final CacheManager cacheManager;
    private final ReceiveMessageRequestFactory factory;
    private final Duration sleepingTime;
    private final Flux whileLoopFluxProvider;
    private final SqsAsyncClient sqsAsyncClient;

    public I18nMessagesCacheRefresher(
            CacheManager cacheManager,
            Duration sleepingTime,
            Flux whileLoopFluxProvider,
            ReceiveMessageRequestFactory factory,
            SqsAsyncClient sqsAsyncClient) {
        this.cacheManager = cacheManager;
        this.sleepingTime = sleepingTime;
        this.whileLoopFluxProvider = whileLoopFluxProvider;
        this.factory = factory;
        this.sqsAsyncClient = sqsAsyncClient;
    }

    public Flux listen() {
        return whileLoopFluxProvider
                .delayElements(sleepingTime)
                .log()
                .flatMap(req -> handleMessage())
                .log()
                .flatMap(signal -> Mono.fromCallable(() -> {
                            logger.info("cache refresh event received ");
                            return Objects.requireNonNull(cacheManager.getCache("account-service-ui.i18n.messages")).invalidate();
                        }
                ))
                .doOnComplete(() -> logger.info("subscription completed"))
                .doOnCancel(() -> logger.info("subscription cancelled"))
                .doOnSubscribe((s) -> logger.info("subscription started"))
                .doOnError(Exception.class, (e) -> logger.error("subscription error: ", e));
    }

    private Mono<List<DeleteMessageResponse>> handleMessage() {
        return Flux.from(fromCompletionStage(sqsAsyncClient.receiveMessage(factory.makeAReceiveMessageRequest())))
                .flatMap(response -> Flux.fromIterable(response.messages()))
                .flatMap(message -> fromCompletionStage(sqsAsyncClient.deleteMessage(factory.makeADeleteMessageRequest(message.receiptHandle()))))
                .collectList()
                .flatMap(deleteMessageResponses -> deleteMessageResponses.size() == 0 ? Mono.empty() : Mono.just(deleteMessageResponses));
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        listen().doOnError(Exception.class, (e) -> {
            try {
                this.run(null);
            } catch (Exception exception) {
                logger.error("subscription error: ", e);
                logger.error("going to resubscribe again ");
            }
        }).subscribe();
    }

}
