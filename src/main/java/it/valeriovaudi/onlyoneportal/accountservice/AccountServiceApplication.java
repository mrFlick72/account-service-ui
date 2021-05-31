package it.valeriovaudi.onlyoneportal.accountservice;

import io.micrometer.core.instrument.MeterRegistry;
import it.valeriovaudi.vauthenticator.security.clientsecuritystarter.security.RedisOAuth2AuthorizedClientService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

@EnableCaching
@SpringBootApplication
public class AccountServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
    }

    @Bean
    MeterRegistryCustomizer<MeterRegistry> configurer(@Value("${spring.application.name:}") String applicationName) {
        return (registry) -> registry.config().commonTags("application", applicationName);
    }


    @Bean
    public OAuth2AuthorizedClientService redisOAuth2AuthorizedClientService(RedisTemplate redisTemplate,
                                                                            ClientRegistrationRepository clientRegistrationRepository) {
        return new RedisOAuth2AuthorizedClientService(redisTemplate, clientRegistrationRepository);
    }
}
