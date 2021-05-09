package it.valeriovaudi.onlyoneportal.accountservice.web.endpoint;

import it.valeriovaudi.onlyoneportal.accountservice.domain.repository.MessageRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class I18nMessagesEndPoint {

    private final MessageRepository messageRepository;

    public I18nMessagesEndPoint(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/messages")
    public Map<String, String> i18nMessagesEndPointRoute() {
        return messageRepository.messages();
    }

}
