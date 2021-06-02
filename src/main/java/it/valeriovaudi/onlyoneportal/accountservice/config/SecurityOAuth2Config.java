package it.valeriovaudi.onlyoneportal.accountservice.config;

import it.valeriovaudi.vauthenticator.security.clientsecuritystarter.filter.BearerTokenInterceptor;
import it.valeriovaudi.vauthenticator.security.clientsecuritystarter.filter.OAuth2TokenResolver;
import it.valeriovaudi.vauthenticator.security.clientsecuritystarter.user.VAuthenticatorOAuth2User;
import it.valeriovaudi.vauthenticator.security.clientsecuritystarter.user.VAuthenticatorOidcUserService;
import it.valeriovaudi.vauthenticator.security.clientsecuritystarter.user.VAuthenticatorReactiveOidcUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcReactiveOAuth2UserService;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.CustomUserTypesOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.ReactiveOAuth2UserService;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.*;
import org.springframework.security.web.server.header.ClearSiteDataServerHttpHeadersWriter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

import static org.springframework.security.web.server.header.ClearSiteDataServerHttpHeadersWriter.Directive.CACHE;
import static org.springframework.security.web.server.header.ClearSiteDataServerHttpHeadersWriter.Directive.COOKIES;

@EnableWebSecurity
public class SecurityOAuth2Config extends WebSecurityConfigurerAdapter {

    @Value("${vauthenticator.client.registrationId}")
    private String registrationId;

    @Value("${granted-role.account-service}")
    private String grantedRole;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable().and().csrf().disable()
                .authorizeRequests().mvcMatchers("/actuator/**", "/oidc_logout.html").permitAll()
                .and()
                .authorizeRequests().anyRequest().hasAnyRole(grantedRole)
                .and().oauth2Login().defaultSuccessUrl("/site/index.html")
                .userInfoEndpoint()
                .oidcUserService(vAuthenticatorOidcUserService());
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