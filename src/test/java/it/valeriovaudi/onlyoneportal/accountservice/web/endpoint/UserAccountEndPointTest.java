package it.valeriovaudi.onlyoneportal.accountservice.web.endpoint;

import it.valeriovaudi.onlyoneportal.accountservice.TestingFixture;
import it.valeriovaudi.onlyoneportal.accountservice.domain.UpdateAccount;
import it.valeriovaudi.onlyoneportal.accountservice.domain.repository.AccountRepository;
import it.valeriovaudi.onlyoneportal.accountservice.support.WithMockOidcUser;
import it.valeriovaudi.onlyoneportal.accountservice.web.representation.Account;
import it.valeriovaudi.vauthenticator.security.clientsecuritystarter.session.management.OAuth2AuthorizationRequestResolverWithSessionState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static it.valeriovaudi.onlyoneportal.accountservice.TestingFixture.ACCOUNT_MAIL;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = {UserAccountEndPoint.class, AdapterTestConfig.class})
class UserAccountEndPointTest {
    private final static String ENDPOINT_PREFIX = "/user-account";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private OAuth2AuthorizationRequestResolverWithSessionState oAuth2AuthorizationRequestResolverWithSessionState;

    @MockBean
    private UpdateAccount updateAccount;

    @MockBean
    private ClientRegistrationRepository clientRegistrationRepository;

    @MockBean
    private OAuth2AuthorizedClientService redisOAuth2AuthorizedClientService;

    @MockBean
    private RestTemplate budgetRestTemplate;

    @Test
    @WithMockOidcUser(email = ACCOUNT_MAIL, authorities = "ACCOUNT")
    void whenGetAUserAccountDetails() throws Exception {
        given(accountRepository.findAnAccount())
                .willReturn(TestingFixture.anAccount());


        mockMvc.perform(get(ENDPOINT_PREFIX))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(TestingFixture.anAccountAsJsonString()));
    }


    @Test
    @WithMockOidcUser(email = ACCOUNT_MAIL, authorities = "ACCOUNT")
    void whenUpdateAUserAccountDetails() throws Exception {
        Account account = TestingFixture.anAccount();

        String content = TestingFixture.anAccountAsJsonString();
        mockMvc.perform(put(ENDPOINT_PREFIX)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content)
        ).andExpect(status().is2xxSuccessful());

        verify(updateAccount).execute(account);
    }
}