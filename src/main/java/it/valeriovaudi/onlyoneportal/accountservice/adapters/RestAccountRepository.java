package it.valeriovaudi.onlyoneportal.accountservice.adapters;

import it.valeriovaudi.onlyoneportal.accountservice.domain.model.Account;
import it.valeriovaudi.onlyoneportal.accountservice.domain.repository.AccountRepository;

public class RestAccountRepository implements AccountRepository {
    @Override
    public Account findByMail(String mail) {
        return null;
    }

    @Override
    public Void save(Account account) {
        return null;
    }


}
