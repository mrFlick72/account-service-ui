package it.valeriovaudi.onlyoneportal.accountservice.adapters.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import it.valeriovaudi.onlyoneportal.accountservice.TestingFixture;
import it.valeriovaudi.onlyoneportal.accountservice.domain.repository.AccountRepository;
import it.valeriovaudi.onlyoneportal.accountservice.web.representation.Account;
import it.valeriovaudi.onlyoneportal.accountservice.web.representation.UserInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class VAuthenticatorAccountRepositoryTest {


    private ObjectMapper objectMapper = new ObjectMapper();
    private WireMockServer wireMockServer = new WireMockServer(WireMockConfiguration.options().dynamicPort());

    AccountRepository restMessageRepository;

    @BeforeEach
    void setUp() {
        wireMockServer.start();
        configureFor("localhost", wireMockServer.port());

        restMessageRepository = new VAuthenticatorAccountRepository(
                wireMockServer.baseUrl(),
                new RestTemplate()
        );
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }


    @Test
    void whenFindAnAccount() throws JsonProcessingException {
        UserInfo userInfo = TestingFixture.aUserInfo();
        Account account = TestingFixture.anAccount();
        String body = objectMapper.writeValueAsString(userInfo);
        StubMapping stubMapping = stubFor(
                get(urlEqualTo("/userinfo"))
                        .willReturn(aResponse()
                                .withHeader("Content-Type", "application/json")
                                .withBody(body))
        );

        wireMockServer.addStubMapping(stubMapping);
        Assertions.assertEquals(
                restMessageRepository.findAnAccount(),
                account
        );
    }

    @Test
    void whenSaveAnAccount() throws JsonProcessingException {
        Account account = TestingFixture.anAccount();
        String body = objectMapper.writeValueAsString(account);
        StubMapping stubMapping = stubFor(
                put(urlEqualTo("/api/accounts"))
                        .withRequestBody(equalTo(body))
                        .withHeader("Content-Type", equalTo("application/json"))
        );

        wireMockServer.addStubMapping(stubMapping);
        restMessageRepository.save(account);

        wireMockServer.verify(
                putRequestedFor(urlEqualTo("/api/accounts"))
                        .withHeader("Content-Type", equalTo("application/json"))
                        .withRequestBody(equalTo(body))
        );
    }
}