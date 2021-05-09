package it.valeriovaudi.onlyoneportal.accountservice.web.adapter;

import it.valeriovaudi.onlyoneportal.accountservice.domain.model.Account;
import it.valeriovaudi.onlyoneportal.accountservice.domain.model.Date;
import it.valeriovaudi.onlyoneportal.accountservice.domain.model.Phone;
import it.valeriovaudi.onlyoneportal.accountservice.web.representation.AccountRepresentation;

import java.util.Locale;

public class AccountAdapter {

    public AccountRepresentation domainToRepresentationModel(Account account) {
        return new AccountRepresentation(account.getFirstName(),
                account.getLastName(),
                account.getBirthDate().formattedDate(),
                account.getMail(),
                account.getPhone().formattedPhone());
    }


    public Account representationModelToDomainModel(AccountRepresentation accountRepresentation) {
        return new Account(accountRepresentation.getFirstName(),
                accountRepresentation.getLastName(),
                Date.dateFor(accountRepresentation.getBirthDate()),
                accountRepresentation.getMail(),
                Phone.phoneFor(accountRepresentation.getPhone()),
                Locale.ENGLISH);
    }

}
