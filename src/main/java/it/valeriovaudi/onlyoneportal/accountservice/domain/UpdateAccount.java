package it.valeriovaudi.onlyoneportal.accountservice.domain;

import it.valeriovaudi.onlyoneportal.accountservice.domain.model.Account;
import it.valeriovaudi.onlyoneportal.accountservice.domain.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateAccount {
    private final static Logger LOGGER = LoggerFactory.getLogger(UpdateAccount.class);

    private final AccountRepository accountRepository;

    public UpdateAccount(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void execute(Account account) {
        LOGGER.info("fire account update event use case level");
        accountRepository.update(account);
    }


}
