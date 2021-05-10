package it.valeriovaudi.onlyoneportal.accountservice.domain.repository;

import it.valeriovaudi.onlyoneportal.accountservice.web.representation.Account;

import java.util.Optional;

public interface AccountRepository {

    Account findAnAccount();

    void save(Account account);
}
