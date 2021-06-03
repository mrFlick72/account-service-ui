package it.valeriovaudi.onlyoneportal.accountservice.web.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.valeriovaudi.onlyoneportal.accountservice.adapters.repository.RestMessageRepository;
import it.valeriovaudi.onlyoneportal.accountservice.support.WithMockOidcUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static it.valeriovaudi.onlyoneportal.accountservice.TestingFixture.ACCOUNT_MAIL;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = {I18nMessagesEndPoint.class})
class I18nMessagesEndPointTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestMessageRepository messageRepository;

    @MockBean
    private ClientRegistrationRepository clientRegistrationRepository;

    @MockBean
    private OAuth2AuthorizedClientService redisOAuth2AuthorizedClientService;

    @MockBean
    private RestTemplate budgetRestTemplate;

    @Test
    @WithMockOidcUser(email = ACCOUNT_MAIL, authorities = "ACCOUNT")
    void whenI18nAreFound() throws Exception {
        var objectMapper = new ObjectMapper();
        Map<String, String> expected = Map.of("key1", "prop1");

        given(messageRepository.messages())
                .willReturn(expected);

        mockMvc.perform(get("/messages"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));

        verify(messageRepository).messages();
    }
}