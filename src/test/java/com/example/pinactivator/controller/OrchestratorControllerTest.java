package com.example.pinactivator.controller;

import com.example.pinactivator.controller.OrchestratorController;
import com.example.pinactivator.model.ActivationStatus;
import com.example.pinactivator.model.PinActivationRequest;
import com.example.pinactivator.model.PinActivationResponse;
import com.example.pinactivator.service.Orchestrator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class OrchestratorControllerTest {

    @Mock
    private Orchestrator orchestrator;

    @InjectMocks
    private OrchestratorController controller;

    @Test
    public void testSuccessfulActivation() {
        // Create a valid PinActivationRequest
        PinActivationRequest request = new PinActivationRequest("12345", "AA:BB:CC:DD:EE:FF");

        // Mock orchestrator behavior to return ACTIVE status
        Mockito.when(orchestrator.activatePin(request)).thenReturn(ActivationStatus.ACTIVE);

        // Send the request and capture the response
        ResponseEntity<PinActivationResponse> response = controller.activatePinTerminal(request);

        // Assert the response status and body
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        Assertions.assertEquals(response.getBody().getStatus(), ActivationStatus.ACTIVE);
    }

    @Test
    public void testActivationError() {
        // Create a valid PinActivationRequest
        PinActivationRequest request = new PinActivationRequest("12345", "AA:BB:CC:DD:EE:FF");

        // Mock orchestrator behavior to throw an exception
        Mockito.when(orchestrator.activatePin(request)).thenThrow(new RuntimeException("Activation failed"));

        // Send the request and capture the response
        ResponseEntity<PinActivationResponse> response = controller.activatePinTerminal(request);

        // Assert the response status
        Assertions.assertEquals(response.getStatusCode(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
