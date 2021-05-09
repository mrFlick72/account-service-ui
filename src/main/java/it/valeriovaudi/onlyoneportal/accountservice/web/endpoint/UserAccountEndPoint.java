package it.valeriovaudi.onlyoneportal.accountservice.web.endpoint;

import it.valeriovaudi.onlyoneportal.accountservice.domain.UpdateAccount;
import it.valeriovaudi.onlyoneportal.accountservice.domain.repository.AccountRepository;
import it.valeriovaudi.onlyoneportal.accountservice.web.adapter.AccountAdapter;
import it.valeriovaudi.onlyoneportal.accountservice.web.representation.AccountRepresentation;
import it.valeriovaudi.vauthenticator.security.clientsecuritystarter.user.VAuthenticatorUserNameResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;
import reactor.util.function.Tuple2;

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
    private final AccountAdapter accountAdapter;

    public UserAccountEndPoint(VAuthenticatorUserNameResolver vAuthenticatorUserNameResolver,
                               AccountRepository accountRepository,
                               UpdateAccount updateAccount,
                               AccountAdapter accountAdapter) {
        this.vAuthenticatorUserNameResolver = vAuthenticatorUserNameResolver;
        this.accountRepository = accountRepository;
        this.updateAccount = updateAccount;
        this.accountAdapter = accountAdapter;
    }

    @Bean
    public RouterFunction accountSiteEndPointRoute() {
        return RouterFunctions.route()
                .GET(ENDPOINT_PREFIX,
                        serverRequest -> serverRequest.principal()
                                .map(vAuthenticatorUserNameResolver::getUserNameFor)
                                .map(accountRepository::findByMail)
                                .map(accountAdapter::domainToRepresentationModel)
                                .map(accountRepresentation -> ServerResponse.ok().body(accountRepresentation))
                                .orElse(ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build())
                )

                .PUT(ENDPOINT_PREFIX,
                        serverRequest -> serverRequest.principal()
                                .map(vAuthenticatorUserNameResolver::getUserNameFor)
                                .flatMap(fromDomainToRepresentation(serverRequest))
                                .map(accountAdapter::representationModelToDomainModel)
                                .map(account -> {
                                    updateAccount.execute(account);
                                    return ServerResponse.noContent().build();
                                })
                                .orElse(ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build())
                ).build();
    }

    private Function<String, Optional<AccountRepresentation>> fromDomainToRepresentation(ServerRequest serverRequest) {
        return userName -> {
            try {
                AccountRepresentation body = serverRequest.body(AccountRepresentation.class);
                body.setMail(userName);
                return Optional.of(body);
            } catch (ServletException | IOException e) {
                return Optional.empty();
            }
        };
    }

}