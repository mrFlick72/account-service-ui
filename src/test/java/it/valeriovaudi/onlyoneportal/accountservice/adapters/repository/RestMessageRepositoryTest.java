package it.valeriovaudi.onlyoneportal.accountservice.adapters.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import it.valeriovaudi.onlyoneportal.accountservice.TestingFixture;
import it.valeriovaudi.onlyoneportal.accountservice.config.RestMessageRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;


import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

class RestMessageRepositoryTest {

    private RestMessageRepository restMessageRepository;

    private ObjectMapper objectMapper = new ObjectMapper();
    private WireMockServer wireMockServer = new WireMockServer(WireMockConfiguration.options().dynamicPort());

    @BeforeEach
    void setUp() {
        wireMockServer.start();
        configureFor("localhost", wireMockServer.port());

        restMessageRepository = new RestMessageRepository(
                wireMockServer.baseUrl(),
                "account-service",
                new RestTemplate()
        );
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }

  /*  @Test()
    void whenCacheIsEmpty() throws JsonProcessingException {
        String body = objectMapper.writeValueAsString(TestingFixture.i18nsMessage());

        StubMapping stubMapping = stubFor(
                get(urlPathEqualTo("/messages/account-service"))
                        .willReturn(aResponse()
                                .withHeader("Content-Type", "application/json")
                                .withBody(body)));

        wireMockServer.addStubMapping(stubMapping);

        StepVerifier.create(restMessageRepository.messages())
                .expectNext(TestingFixture.i18nsMessage())
                .verifyComplete();

        wireMockServer.verify(getRequestedFor(urlEqualTo("/messages/account-service")));
    }

    @Test
    void whenCacheIsFull() {
        // fill cache
        cacheManager.updateCache(TestingFixture.i18nsMessage()).block();
        StepVerifier.create(cacheManager.updateCache(TestingFixture.i18nsMessage()))
                .expectNext(TestingFixture.i18nsMessage())
                .verifyComplete();

        StepVerifier.create(restMessageRepository.messages())
                .expectNext(TestingFixture.i18nsMessage())
                .verifyComplete();
    }*/
}