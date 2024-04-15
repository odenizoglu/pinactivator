package com.example.pinactivator.service;

import com.example.pinactivator.model.ActivationStatus;
import com.example.pinactivator.service.PinActivationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
public class PinActivationServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PinActivationService service;

    @Value("${southbound.url}") // Inject the mocked value here (optional)
    private String mockSouthboundUrl;

    @Test
    public void testSuccessfulActivation() {
        String customerId = "12345";
        String macAddress = "AA:BB:CC:DD:EE:FF";
        String requestBody = "{\"customerId\": \"" + customerId + "\", \"macAddress\": \"" + macAddress + "\"}";
        String expectedResponse = "{\"status\": \"activated\"}";

        Mockito.when(restTemplate.postForEntity(mockSouthboundUrl, requestBody, String.class))
                .thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.CREATED));

        ActivationStatus status = service.activatePinTerminal(customerId, macAddress);

        Assertions.assertEquals(status, ActivationStatus.ACTIVE);
    }

    @Test
    public void testUnSuccessfulActivation() {
        String customerId = "12345";
        String macAddress = "AA:BB:CC:DD:EE:AA";
        String requestBody = "{\"customerId\": \"" + customerId + "\", \"macAddress\": \"" + macAddress + "\"}";
        String expectedResponse = "{\"status\": \"activated\"}";

        Mockito.when(restTemplate.postForEntity(mockSouthboundUrl, requestBody, String.class))
                .thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.NOT_FOUND));

        ActivationStatus status = service.activatePinTerminal(customerId, macAddress);

        Assertions.assertEquals(status, ActivationStatus.INACTIVE);
    }

    @Test
    public void testConflictActivation() {
        String customerId = "11111";
        String macAddress = "AA:BB:CC:DD:EE:FF";
        String requestBody = "{\"customerId\": \"" + customerId + "\", \"macAddress\": \"" + macAddress + "\"}";
        String expectedResponse = "{\"status\": \"activated\"}";

        Mockito.when(restTemplate.postForEntity(mockSouthboundUrl, requestBody, String.class))
                .thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.CONFLICT));

        ActivationStatus status = service.activatePinTerminal(customerId, macAddress);

        Assertions.assertEquals(status, ActivationStatus.INACTIVE);
    }

    @Test
    public void testErrorActivation() {
        String customerId = "11111";
        String macAddress = "AA:BB:CC:DD:EE:FF";
        String requestBody = "{\"customerId\": \"" + customerId + "\", \"macAddress\": \"" + macAddress + "\"}";
        String expectedResponse = "{\"status\": \"activated\"}";

        Mockito.when(restTemplate.postForEntity(mockSouthboundUrl, requestBody, String.class))
                .thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.INTERNAL_SERVER_ERROR));

        ActivationStatus status = service.activatePinTerminal(customerId, macAddress);

        Assertions.assertEquals(status, ActivationStatus.INACTIVE);
    }


}
