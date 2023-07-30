package it.valeriovaudi.onlyoneportal.accountservice.config;

import com.vauthenticator.springbootclientstarter.filter.BearerTokenInterceptor;
import com.vauthenticator.springbootclientstarter.filter.OAuth2TokenResolver;
import it.valeriovaudi.onlyoneportal.accountservice.adapters.repository.VAuthenticatorAccountRepository;
import it.valeriovaudi.onlyoneportal.accountservice.domain.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration
public class RepositoryConfig {


    @Bean
    public AccountRepository accountRepository(
            @Value("${vauthenticator.backChannelHost}") String baseUrl,
            RestTemplate accountRestTemplate) {
        return new VAuthenticatorAccountRepository(baseUrl, accountRestTemplate);
    }

    @Bean
    public RestTemplate accountRestTemplate(OAuth2TokenResolver oAuth2TokenResolver) {
        return new RestTemplateBuilder()
                .additionalInterceptors(new BearerTokenInterceptor(oAuth2TokenResolver))
                .build();
    }
}
