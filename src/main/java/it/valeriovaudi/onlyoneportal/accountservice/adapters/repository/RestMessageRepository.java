package it.valeriovaudi.onlyoneportal.accountservice.adapters.repository;

import it.valeriovaudi.onlyoneportal.accountservice.domain.repository.MessageRepository;
import org.reactivestreams.Publisher;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class RestMessageRepository implements MessageRepository {

    private final String i18nBaseUrl;
    private final String applicationId;
    private final RestTemplate i18nRestTemplate;

    public RestMessageRepository(String i18nBaseUrl,
                                 String applicationId,
                                 RestTemplate i18nRestTemplate) {
        this.i18nBaseUrl = i18nBaseUrl;
        this.applicationId = applicationId;
        this.i18nRestTemplate = i18nRestTemplate;
    }

    @Override
    @Cacheable(cacheNames = "account-service-ui.i18n.messages", key = "'i18n.messages'")
    public Map<String, String> messages() {
        return i18nRestTemplate.getForObject(i18nBaseUrl + "/messages/" + applicationId, HashMap.class);
    }
}
