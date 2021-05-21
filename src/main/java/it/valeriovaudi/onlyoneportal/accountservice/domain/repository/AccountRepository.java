package it.valeriovaudi.onlyoneportal.accountservice.domain.repository;

import it.valeriovaudi.onlyoneportal.accountservice.web.representation.Account;

public interface AccountRepository {

    Account findAnAccount();

    void save(Account account);
}
