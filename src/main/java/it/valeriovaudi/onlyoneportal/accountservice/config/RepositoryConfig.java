package it.valeriovaudi.onlyoneportal.accountservice.config;

import it.valeriovaudi.onlyoneportal.accountservice.adapters.cache.I18nMessagesCacheRefresher;
import it.valeriovaudi.onlyoneportal.accountservice.adapters.cache.ReceiveMessageRequestFactory;
import it.valeriovaudi.onlyoneportal.accountservice.adapters.repository.RestMessageRepository;
import it.valeriovaudi.onlyoneportal.accountservice.adapters.repository.VAuthenticatorAccountRepository;
import it.valeriovaudi.onlyoneportal.accountservice.domain.repository.AccountRepository;
import it.valeriovaudi.onlyoneportal.accountservice.domain.repository.MessageRepository;
import it.valeriovaudi.vauthenticator.security.clientsecuritystarter.filter.BearerTokenInterceptor;
import it.valeriovaudi.vauthenticator.security.clientsecuritystarter.filter.OAuth2TokenResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

import java.time.Duration;


@Configuration
public class RepositoryConfig {

    @Bean
    public I18nMessagesCacheRefresher reactiveCacheUpdaterListener(@Value("${i18n-messages.cache.updater.listener.sleeping:10m}") Duration sleeping,
                                                                   CacheManager cacheManager,
                                                                   ReceiveMessageRequestFactory receiveMessageRequestFactory,
                                                                   SqsAsyncClient sqsAsyncClient) {
        return new I18nMessagesCacheRefresher(cacheManager, sleeping, Flux.just(1).repeat(), receiveMessageRequestFactory, sqsAsyncClient);
    }


    @Bean
    public ReceiveMessageRequestFactory receiveMessageRequestFactory(@Value("${i18n-messages.cache.updater.listener.queueUrl}") String queueUrl,
                                                                     @Value("${i18n-messages.cache.updater.listener.maxNumberOfMessages}") Integer maxNumberOfMessages,
                                                                     @Value("${i18n-messages.cache.updater.listener.visibilityTimeout}") Integer visibilityTimeout,
                                                                     @Value("${i18n-messages.cache.updater.listener.waitTimeSeconds}") Integer waitTimeSeconds

    ) {
        return new ReceiveMessageRequestFactory(queueUrl, maxNumberOfMessages, visibilityTimeout, waitTimeSeconds);
    }

    @Bean
    public AccountRepository accountRepository(
            @Value("${vauthenticator.backChannelHost}") String baseUrl,
            RestTemplate accountRestTemplate) {
        return new VAuthenticatorAccountRepository(baseUrl, accountRestTemplate);
    }

    @Bean
    public AwsCredentialsProvider awsCredentialsProvider() {
        return EnvironmentVariableCredentialsProvider.create();
    }


    @Bean
    public SqsAsyncClient sqsAsyncClient(@Value("${aws.region}") String awsRegion,
                                         AwsCredentialsProvider awsCredentialsProvider) {
        return SqsAsyncClient.builder()
                .credentialsProvider(awsCredentialsProvider)
                .region(Region.of(awsRegion))
                .build();
    }

    @Bean
    public RestTemplate i18nRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public RestTemplate accountRestTemplate(OAuth2TokenResolver oAuth2TokenResolver) {
        return new RestTemplateBuilder()
                .additionalInterceptors(new BearerTokenInterceptor(oAuth2TokenResolver))
                .build();
    }

    @Bean
    public MessageRepository messageRepository(RestTemplate i18nRestTemplate,
                                               @Value("${i18n-messages.base-url:http://i18n-messages}") String i18nBaseUrl) {
        return new RestMessageRepository(i18nBaseUrl, "account-service", i18nRestTemplate);
    }
}
