package it.valeriovaudi.onlyoneportal.accountservice.adapters.repository;

import it.valeriovaudi.onlyoneportal.accountservice.domain.repository.AccountRepository;
import it.valeriovaudi.onlyoneportal.accountservice.web.representation.Account;
import it.valeriovaudi.onlyoneportal.accountservice.web.representation.UserInfo;
import org.springframework.web.client.RestTemplate;

public class VAuthenticatorAccountRepository implements AccountRepository {
    private final String baseUrl;
    private final RestTemplate restTemplate;

    public VAuthenticatorAccountRepository(String baseUrl, RestTemplate restTemplate) {
        this.baseUrl = baseUrl;
        this.restTemplate = restTemplate;
    }

    @Override
    public Account findAnAccount() {
        UserInfo userInfo = restTemplate.getForObject(baseUrl + "/userinfo", UserInfo.class);
        return new Account(
                userInfo.getFirstName(),
                userInfo.getLastName(),
                userInfo.getBirthDate(),
                userInfo.getMail(),
                userInfo.getPhone()
        );
    }

    @Override
    public void save(Account account) {
        restTemplate.put(baseUrl + "/api/accounts", account);
    }

}
