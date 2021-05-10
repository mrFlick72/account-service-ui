package it.valeriovaudi.onlyoneportal.accountservice.adapters.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import it.valeriovaudi.onlyoneportal.accountservice.TestingFixture;
import it.valeriovaudi.onlyoneportal.accountservice.web.representation.Account;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;

class RestAccountRepositoryTest {

    private ObjectMapper objectMapper = new ObjectMapper();
    private WireMockServer wireMockServer = new WireMockServer(WireMockConfiguration.options().dynamicPort());

    RestAccountRepository restMessageRepository;

    @BeforeEach
    void setUp() {
        wireMockServer.start();
        configureFor("localhost", wireMockServer.port());

        restMessageRepository = new RestAccountRepository(
                wireMockServer.baseUrl(),
                "/account/user-account",
                new RestTemplate()
        );
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }


    @Test
    void whenFindAnAccount() throws JsonProcessingException {
        Account account = TestingFixture.anAccount();
        String body = objectMapper.writeValueAsString(account);
        StubMapping stubMapping = stubFor(
                get(urlEqualTo("/account/user-account"))
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
                put(urlEqualTo("/account/user-account"))
                        .withRequestBody(equalTo(body))
                        .withHeader("Content-Type", equalTo("application/json"))
        );

        wireMockServer.addStubMapping(stubMapping);
        restMessageRepository.save(account);

        wireMockServer.verify(
                putRequestedFor(urlEqualTo("/account/user-account"))
                        .withHeader("Content-Type", equalTo("application/json"))
                        .withRequestBody(equalTo(body))
        );
    }
}