package com.example.pinactivator.service;

import com.example.pinactivator.model.ActivationStatus;
import com.example.pinactivator.model.PinActivationRequest;
import com.example.pinactivator.service.Orchestrator;
import com.example.pinactivator.service.PinActivationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrchestratorTest {

    @Mock
    private PinActivationService pinActivationService;

    @InjectMocks
    private Orchestrator orchestrator;

    @Test
    public void testSuccessfulActivation() {
        // Create a valid PinActivationRequest
        PinActivationRequest request = new PinActivationRequest("12345", "AA:BB:CC:DD:EE:FF");

        // Mock pinActivationService behavior to return ACTIVE status
        Mockito.when(pinActivationService.activatePinTerminal(request.getCustomerId(), request.getMacAddress()))
                .thenReturn(ActivationStatus.ACTIVE);

        // Call the Orchestrator's activatePin method
        ActivationStatus status = orchestrator.activatePin(request);

        // Assert that the returned status is ACTIVE
        Assertions.assertEquals(status, ActivationStatus.ACTIVE);
    }

    @Test
    public void testUnSuccessfulActivation() {
        // Create a invalid PinActivationRequest
        PinActivationRequest request = new PinActivationRequest("12345", "AA:BB:CC:DD:EE:AA");

        // Mock pinActivationService behavior to return ACTIVE status
        Mockito.when(pinActivationService.activatePinTerminal(request.getCustomerId(), request.getMacAddress()))
                .thenReturn(ActivationStatus.INACTIVE);

        // Call the Orchestrator's activatePin method
        ActivationStatus status = orchestrator.activatePin(request);

        // Assert that the returned status is ACTIVE
        Assertions.assertEquals(status, ActivationStatus.INACTIVE);
    }

}
