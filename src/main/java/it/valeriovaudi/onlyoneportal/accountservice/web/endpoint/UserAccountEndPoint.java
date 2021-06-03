package it.valeriovaudi.onlyoneportal.accountservice.web.endpoint;

import it.valeriovaudi.onlyoneportal.accountservice.domain.UpdateAccount;
import it.valeriovaudi.onlyoneportal.accountservice.domain.repository.AccountRepository;
import it.valeriovaudi.onlyoneportal.accountservice.web.representation.Account;
import it.valeriovaudi.vauthenticator.security.clientsecuritystarter.user.VAuthenticatorUserNameResolver;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@RestController
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

    @GetMapping(ENDPOINT_PREFIX)
    public ResponseEntity getAccountDetails() {
        return ok().body(accountRepository.findAnAccount());
    }

    @PutMapping(ENDPOINT_PREFIX)
    public ResponseEntity updateAccountDetails(Principal principal,
                                               @RequestBody Account account) {

        String username = vAuthenticatorUserNameResolver.getUserNameFor(principal);
        account.setMail(username);
        updateAccount.execute(account);
        return noContent().build();
    }

}