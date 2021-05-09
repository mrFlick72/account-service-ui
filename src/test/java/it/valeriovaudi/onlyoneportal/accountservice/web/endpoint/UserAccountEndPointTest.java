package it.valeriovaudi.onlyoneportal.accountservice.web.endpoint;

import it.valeriovaudi.onlyoneportal.accountservice.TestingFixture;
import it.valeriovaudi.onlyoneportal.accountservice.domain.UpdateAccount;
import it.valeriovaudi.onlyoneportal.accountservice.domain.model.Account;
import it.valeriovaudi.onlyoneportal.accountservice.domain.repository.AccountRepository;
import it.valeriovaudi.onlyoneportal.accountservice.support.WithMockOidcUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    private UpdateAccount updateAccount;

    @Test
    @WithMockOidcUser(email = "user.mail@mail.com", authorities = "ACCOUNT" )
    void whenGetAUserAccountDetails() throws Exception {
        given(accountRepository.findByMail("user.mail@mail.com" ))
                .willReturn(TestingFixture.anAccount());


        mockMvc.perform(get(ENDPOINT_PREFIX))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(TestingFixture.anAccountAsJsonString()));
    }


    @Test
    @WithMockOidcUser(email = "user.mail@mail.com", authorities = "ACCOUNT" )
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