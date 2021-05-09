package it.valeriovaudi.onlyoneportal.accountservice.support;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OAuth2WithSecurityContextFactory implements WithSecurityContextFactory<WithMockOidcUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockOidcUser withMockOidcUser) {
        var securityContext = SecurityContextHolder.createEmptyContext();
        OAuth2AuthenticationToken principal = principalFor(withMockOidcUser.email(), withMockOidcUser.authorities());
        securityContext.setAuthentication(principal);
        securityContext.getAuthentication().setAuthenticated(true);

        return securityContext;
    }

    public static OAuth2AuthenticationToken principalFor(String email, String... authorities) {
        var authorityGrants = Stream.of(authorities)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());


        var user = new DefaultOidcUser(authorityGrants, new OidcIdToken("A_TOKEN",
                Instant.now(),
                Instant.now().plusSeconds(100),
                Map.of("email", email)),
                "email"
        );

        return new OAuth2AuthenticationToken(user, authorityGrants, "client" );
    }
}
