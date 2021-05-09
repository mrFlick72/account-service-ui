package it.valeriovaudi.onlyoneportal.accountservice.domain.repository;

import it.valeriovaudi.onlyoneportal.accountservice.domain.model.Account;
import org.reactivestreams.Publisher;


public interface AccountRepository {

    Account findByMail(String mail);

    Void save(Account account);

    Void  update(Account account);
}
