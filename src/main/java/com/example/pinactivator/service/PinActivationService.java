package com.example.pinactivator.service;

import com.example.pinactivator.model.ActivationStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class PinActivationService {


    @Value("${southbound.url}")
    private String southboundUrl;

    private RestTemplate restTemplate;

    public PinActivationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ActivationStatus activatePinTerminal(String customerId, String macAddress) {
        log.info("Activating PIN terminal for customer ID {} with MAC address {}", customerId, macAddress);
        try {
            // Create request body
            String requestBody = "{\"customerId\": \"" + customerId + "\", \"macAddress\": \"" + macAddress + "\"}";

            // Make HTTP POST request to the southbound system
            ResponseEntity<String> response = restTemplate.postForEntity(southboundUrl, requestBody, String.class);

            // Handle the response
            if (response.getStatusCode() == HttpStatus.CREATED) {
                log.info("Terminal activated successfully for customer ID {} with MAC address {}", customerId, macAddress);
                return ActivationStatus.ACTIVE;
            } else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                log.warn("Terminal not found for customer ID {} with MAC address {}", customerId, macAddress);
                return ActivationStatus.INACTIVE;
            } else if (response.getStatusCode() == HttpStatus.CONFLICT) {
                log.warn("Terminal already activated or conflict detected for customer ID {} with MAC address {}", customerId, macAddress);
                return ActivationStatus.INACTIVE;
            } else {
                log.error("Unexpected response from southbound system: {}", response.getStatusCode());
                return ActivationStatus.INACTIVE;
            }
        } catch (HttpClientErrorException e) {
            log.error("Error while activating terminal: {}", e.getStatusCode());
            return ActivationStatus.INACTIVE;
        } catch (Exception e) {
            log.error("Error while activating terminal", e.toString());
            return ActivationStatus.INACTIVE;
        }
    }
}
