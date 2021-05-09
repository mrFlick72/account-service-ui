package it.valeriovaudi.onlyoneportal.accountservice.config;

import it.valeriovaudi.onlyoneportal.accountservice.domain.repository.MessageRepository;
import org.reactivestreams.Publisher;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class RestMessageRepository implements MessageRepository {

    public RestMessageRepository(String i18nBaseUrl,
                                 String applicationId,
                                 RestTemplate i18nRestTemplate) {
    }

    @Override
    public Map<String, String> messages() {
        return null;
    }
}
