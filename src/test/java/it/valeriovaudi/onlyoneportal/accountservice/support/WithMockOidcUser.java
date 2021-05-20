package it.valeriovaudi.onlyoneportal.accountservice.support;


import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@WithSecurityContext(factory = OAuth2WithSecurityContextFactory.class)
public @interface WithMockOidcUser {
    String email() default "A_USER_NAME";

    String[] authorities();
}

