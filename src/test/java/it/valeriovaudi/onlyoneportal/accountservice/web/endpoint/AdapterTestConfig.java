package it.valeriovaudi.onlyoneportal.accountservice.web.endpoint;

import it.valeriovaudi.vauthenticator.security.clientsecuritystarter.user.VAuthenticatorUserNameResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AdapterTestConfig {
    @Bean
    public VAuthenticatorUserNameResolver vAuthenticatorUserNameResolver(){
        return new VAuthenticatorUserNameResolver("email");
    }
}
