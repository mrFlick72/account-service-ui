package it.valeriovaudi.onlyoneportal.accountservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.valeriovaudi.onlyoneportal.accountservice.domain.UpdateAccount;
import it.valeriovaudi.onlyoneportal.accountservice.domain.repository.AccountRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public UpdateAccount updateAccount(AccountRepository accountRepository) {
        return new UpdateAccount(accountRepository);
    }
}
