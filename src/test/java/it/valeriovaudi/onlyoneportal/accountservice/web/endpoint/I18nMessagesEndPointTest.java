package it.valeriovaudi.onlyoneportal.accountservice.web.endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.valeriovaudi.onlyoneportal.accountservice.domain.repository.MessageRepository;
import it.valeriovaudi.onlyoneportal.accountservice.support.WithMockOidcUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(value = {I18nMessagesEndPoint.class})
class I18nMessagesEndPointTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageRepository messageRepository;


    @Test
    @WithMockOidcUser(email= "AN_EMAIL", authorities = {})
    void whenI18nAreFound() throws Exception {
        var objectMapper = new ObjectMapper();
        Map<String, String> expected = Map.of("key1", "prop1");

        given(messageRepository.messages())
                .willReturn(expected);

        mockMvc.perform(get("/messages"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }
}