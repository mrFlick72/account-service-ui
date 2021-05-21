package it.valeriovaudi.onlyoneportal.accountservice.web.endpoint;

import it.valeriovaudi.onlyoneportal.accountservice.domain.UpdateAccount;
import it.valeriovaudi.onlyoneportal.accountservice.domain.repository.AccountRepository;
import it.valeriovaudi.onlyoneportal.accountservice.web.representation.Account;
import it.valeriovaudi.vauthenticator.security.clientsecuritystarter.user.VAuthenticatorUserNameResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Optional;
import java.util.function.Function;

@Configuration
public class UserAccountEndPoint {

    private final static String ENDPOINT_PREFIX = "/user-account";
    private final VAuthenticatorUserNameResolver vAuthenticatorUserNameResolver;
    private final AccountRepository accountRepository;
    private final UpdateAccount updateAccount;

    public UserAccountEndPoint(VAuthenticatorUserNameResolver vAuthenticatorUserNameResolver,
                               AccountRepository accountRepository,
                               UpdateAccount updateAccount) {
        this.vAuthenticatorUserNameResolver = vAuthenticatorUserNameResolver;
        this.accountRepository = accountRepository;
        this.updateAccount = updateAccount;
    }

    @Bean
    public RouterFunction accountSiteEndPointRoute() {
        return RouterFunctions.route()
                .GET(ENDPOINT_PREFIX,
                        serverRequest -> ServerResponse.ok().body(accountRepository.findAnAccount())
                )

                .PUT(ENDPOINT_PREFIX,
                        serverRequest -> serverRequest.principal()
                                .map(vAuthenticatorUserNameResolver::getUserNameFor)
                                .flatMap(fromDomainToRepresentation(serverRequest))
                                .map(account -> {
                                    updateAccount.execute(account);
                                    return ServerResponse.noContent().build();
                                })
                                .orElse(ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build())
                )
                .build();
    }

    private Function<String, Optional<Account>> fromDomainToRepresentation(ServerRequest serverRequest) {
        return userName -> {
            try {
                Account body = serverRequest.body(Account.class);
                body.setMail(userName);
                return Optional.of(body);
            } catch (ServletException | IOException e) {
                return Optional.empty();
            }
        };
    }

}