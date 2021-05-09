package it.valeriovaudi.onlyoneportal.accountservice.config;

import it.valeriovaudi.onlyoneportal.accountservice.web.adapter.AccountAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConverterConfig {

    @Bean
    public AccountAdapter accountAdapter(){
        return new AccountAdapter();
    }
}
