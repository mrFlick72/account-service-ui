package it.valeriovaudi.onlyoneportal.accountservice.config;

import com.vauthenticator.springbootclientstarter.filter.BearerTokenInterceptor;
import com.vauthenticator.springbootclientstarter.filter.OAuth2TokenResolver;
import com.vauthenticator.springbootclientstarter.session.management.OAuth2AuthorizationRequestResolverWithSessionState;
import com.vauthenticator.springbootclientstarter.user.VAuthenticatorOidcUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;

@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
public class OAuth2SecurityConfig {
    private final VAuthenticatorOidcUserService vAuthenticatorOidcUserService;
    private final OAuth2AuthorizationRequestResolverWithSessionState oAuth2AuthorizationRequestResolverWithSessionState;

    @Value("${granted-role.account-service:}")
    private String grantedRole;

    public OAuth2SecurityConfig(VAuthenticatorOidcUserService vAuthenticatorOidcUserService,
                                OAuth2AuthorizationRequestResolverWithSessionState oAuth2AuthorizationRequestResolverWithSessionState) {
        this.vAuthenticatorOidcUserService = vAuthenticatorOidcUserService;
        this.oAuth2AuthorizationRequestResolverWithSessionState = oAuth2AuthorizationRequestResolverWithSessionState;
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .headers(headersConfigurer -> headersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

        http.logout(logoutConfigurer -> logoutConfigurer.deleteCookies("opbs")
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/index"));


        http.oauth2Login(loginConfigurer -> loginConfigurer
                .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.oidcUserService(vAuthenticatorOidcUserService))
                .authorizationEndpoint(authorizationEndpointConfig -> authorizationEndpointConfig.authorizationRequestResolver(oAuth2AuthorizationRequestResolverWithSessionState)));


        http.authorizeHttpRequests(
                authz ->
                        authz.requestMatchers("/actuator/**", "/oidc_logout.html").permitAll()
                                .anyRequest().hasAnyRole(grantedRole)
        );

        return http.build();
    }

    @Bean
    public RestTemplate budgetRestTemplate(OAuth2TokenResolver oAuth2TokenResolver) {
        return new RestTemplateBuilder()
                .additionalInterceptors(new BearerTokenInterceptor(oAuth2TokenResolver))
                .build();
    }

}
