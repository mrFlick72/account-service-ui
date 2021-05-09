package it.valeriovaudi.onlyoneportal.accountservice.config;

import it.valeriovaudi.onlyoneportal.accountservice.adapters.cache.ReceiveMessageRequestFactory;
import it.valeriovaudi.onlyoneportal.accountservice.domain.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;


@Configuration
@EnableConfigurationProperties(R2dbcProperties.class)
public class RepositoryConfig {

    @Bean
    public ReceiveMessageRequestFactory receiveMessageRequestFactory(@Value("${i18n-messages.cache.updater.listener.queueUrl}") String queueUrl,
                                                                     @Value("${i18n-messages.cache.updater.listener.maxNumberOfMessages}") Integer maxNumberOfMessages,
                                                                     @Value("${i18n-messages.cache.updater.listener.visibilityTimeout}") Integer visibilityTimeout,
                                                                     @Value("${i18n-messages.cache.updater.listener.waitTimeSeconds}") Integer waitTimeSeconds

                                                                     ) {
        return new ReceiveMessageRequestFactory(queueUrl, maxNumberOfMessages, visibilityTimeout, waitTimeSeconds);
    }


    @Bean
    public AwsCredentialsProvider awsCredentialsProvider(@Value("${aws.access-key}") String accessKey,
                                                         @Value("${aws.secret-key}") String awsSecretKey) {
        return StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, awsSecretKey));
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
    public RestTemplate i18nRestTemplate(){
        return new RestTemplate();
    }
    @Bean
    public MessageRepository messageRepository(RestTemplate i18nRestTemplate,
                                               @Value("${i18n-messages.base-url:http://i18n-messages}") String i18nBaseUrl,
                                               @Value("${spring.application.name}") String applicationId) {
        return new RestMessageRepository(i18nBaseUrl, applicationId, i18nRestTemplate);
    }
}
