package it.valeriovaudi.onlyoneportal.accountservice.config;

import it.valeriovaudi.vauthenticator.security.clientsecuritystarter.filter.BearerTokenInterceptor;
import it.valeriovaudi.vauthenticator.security.clientsecuritystarter.filter.OAuth2TokenResolver;
import it.valeriovaudi.vauthenticator.security.clientsecuritystarter.session.management.OAuth2AuthorizationRequestResolverWithSessionState;
import it.valeriovaudi.vauthenticator.security.clientsecuritystarter.user.VAuthenticatorOAuth2User;
import it.valeriovaudi.vauthenticator.security.clientsecuritystarter.user.VAuthenticatorOidcUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.CustomUserTypesOAuth2UserService;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@EnableWebSecurity
public class SecurityOAuth2Config extends WebSecurityConfigurerAdapter {
    private final OAuth2AuthorizationRequestResolverWithSessionState oAuth2AuthorizationRequestResolverWithSessionState;

    @Value("${vauthenticator.client.registrationId}")
    private String registrationId;

    @Value("${granted-role.account-service:}")
    private String grantedRole;

    public SecurityOAuth2Config(OAuth2AuthorizationRequestResolverWithSessionState oAuth2AuthorizationRequestResolverWithSessionState) {
        this.oAuth2AuthorizationRequestResolverWithSessionState = oAuth2AuthorizationRequestResolverWithSessionState;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().mvcMatchers("/actuator/**", "/oidc_logout.html").permitAll();

        if (!"".equals(grantedRole)) {
            http.authorizeRequests().anyRequest().hasAnyRole(grantedRole);
        }

        http.oauth2Login().defaultSuccessUrl("/site/index.html")
                .userInfoEndpoint()
                .oidcUserService(vAuthenticatorOidcUserService())
                .and()
                .authorizationEndpoint().authorizationRequestResolver(oAuth2AuthorizationRequestResolverWithSessionState);
    }

    public VAuthenticatorOidcUserService vAuthenticatorOidcUserService() {
        return new VAuthenticatorOidcUserService(new OidcUserService(),
                new CustomUserTypesOAuth2UserService(Map.of(registrationId, VAuthenticatorOAuth2User.class))
        );
    }

    @Bean
    public RestTemplate budgetRestTemplate(OAuth2TokenResolver oAuth2TokenResolver) {
        return new RestTemplateBuilder()
                .additionalInterceptors(new BearerTokenInterceptor(oAuth2TokenResolver))
                .build();
    }

}