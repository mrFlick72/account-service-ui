package it.valeriovaudi.onlyoneportal.accountservice.adapters.repository;

import it.valeriovaudi.onlyoneportal.accountservice.domain.repository.AccountRepository;
import it.valeriovaudi.onlyoneportal.accountservice.web.representation.Account;
import org.springframework.web.client.RestTemplate;

public class RestAccountRepository implements AccountRepository {
    private final String baseUrl;
    private final String path;
    private final RestTemplate restTemplate;

    public RestAccountRepository(String baseUrl, String path, RestTemplate restTemplate) {
        this.baseUrl = baseUrl;
        this.path = path;
        this.restTemplate = restTemplate;
    }

    @Override
    public Account findAnAccount() {
        return restTemplate.getForObject(baseUrl + path, Account.class);
    }

    @Override
    public void save(Account account) {
        restTemplate.put(baseUrl + path, account);
    }

}
