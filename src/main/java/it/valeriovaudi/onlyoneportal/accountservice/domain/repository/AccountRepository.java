package it.valeriovaudi.onlyoneportal.accountservice.domain.repository;

import it.valeriovaudi.onlyoneportal.accountservice.web.representation.Account;

public interface AccountRepository {

    Account findByMail(String mail);

    void save(Account account);
}
