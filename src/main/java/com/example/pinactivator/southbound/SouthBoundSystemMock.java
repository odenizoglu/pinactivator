package com.example.pinactivator.southbound;

import com.github.tomakehurst.wiremock.WireMockServer;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.post;

@Component
public class SouthBoundSystemMock {
    private final WireMockServer wireMockServer;

    @Value("${southbound.port}")
    private int southboundPort;

    private String southboundPortEndPoint = "/activate";

    public SouthBoundSystemMock() {
        this.wireMockServer = new WireMockServer(southboundPort);
        startServer();
    }
    private void startServer(){
        wireMockServer.start();
        configureMockResponseForSuccess();
        configureMockResponseForNotFound();
        configureMockResponseForConflict();
    }

    @PreDestroy
    private void stopServer(){
        wireMockServer.stop();
    }
    public void configureMockResponseForSuccess() {
        wireMockServer.stubFor(post(southboundPortEndPoint)
                .withRequestBody(equalToJson("{\"customerId\": \"12345\", \"macAddress\": \"AA:BB:CC:DD:EE:FF\"}"))
                .willReturn(aResponse()
                        .withStatus(201) // Status: 201 Created
                        .withBody("{\"message\": \"Terminal activated successfully\"}")));
    }

    public void configureMockResponseForNotFound() {
        wireMockServer.stubFor(post(southboundPortEndPoint)
                .withRequestBody(equalToJson("{\"customerId\": \"12345\", \"macAddress\": \"AA:BB:CC:DD:EE:AA\"}"))
                .willReturn(aResponse()
                        .withStatus(404) // Status: 404 Not Found
                        .withBody("{\"message\": \"Terminal not found\"}")));
    }
    public void configureMockResponseForConflict() {
        wireMockServer.stubFor(post(southboundPortEndPoint)
                .withRequestBody(equalToJson("{\"customerId\": \"11111\", \"macAddress\": \"AA:BB:CC:DD:EE:FF\"}"))
                .willReturn(aResponse()
                        .withStatus(409) // Status: 409 Conflict
                        .withBody("{\"message\": \"Terminal already activated or conflict detected\"}")));
    }
}
