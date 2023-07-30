package it.valeriovaudi.onlyoneportal.accountservice.web.endpoint;

import com.vauthenticator.springbootclientstarter.user.VAuthenticatorUserNameResolver;
import it.valeriovaudi.onlyoneportal.accountservice.adapters.date.Dates;
import it.valeriovaudi.onlyoneportal.accountservice.domain.UpdateAccount;
import it.valeriovaudi.onlyoneportal.accountservice.domain.repository.AccountRepository;
import it.valeriovaudi.onlyoneportal.accountservice.web.representation.Account;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.temporal.TemporalAccessor;

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
        Account account = accountRepository.findAnAccount();
        TemporalAccessor parsedBirthDate = Dates.ISO_DATE_FORMATTER.parse(account.birthDate());
        return ok().body(
                new Account(
                        account.firstName(),
                        account.lastName(),
                        Dates.UI_DATE_FORMATTER.format(parsedBirthDate),
                        account.mail(),
                        account.phone()
                )
        );
    }

    @PutMapping(ENDPOINT_PREFIX)
    public ResponseEntity updateAccountDetails(Principal principal,
                                               @RequestBody Account account) {
        TemporalAccessor parsedBirthDate = Dates.UI_DATE_FORMATTER.parse(account.birthDate());
        String username = vAuthenticatorUserNameResolver.getUserNameFor(principal);
        updateAccount.execute(new Account(
                account.firstName(),
                account.lastName(),
                Dates.ISO_DATE_FORMATTER.format(parsedBirthDate),
                username,
                account.phone()
        ));
        return noContent().build();
    }

}