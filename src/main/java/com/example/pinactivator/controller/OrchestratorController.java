package com.example.pinactivator.controller;

import com.example.pinactivator.model.ActivationStatus;
import com.example.pinactivator.model.PinActivationRequest;
import com.example.pinactivator.model.PinActivationResponse;
import com.example.pinactivator.service.Orchestrator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/pin-activation")
public class OrchestratorController {
    private final Orchestrator orchestrator;

    public OrchestratorController(Orchestrator orchestrator) {
        this.orchestrator = orchestrator;
    }

    @PostMapping()
    public ResponseEntity<PinActivationResponse> activatePinTerminal(@RequestBody PinActivationRequest request) {
        try {
            // Call the orchestrator to activate the PIN terminal
            ActivationStatus status = orchestrator.activatePin(request);

            // Determine HTTP status based on activation status
            HttpStatus httpStatus = status.equals(ActivationStatus.ACTIVE) ? HttpStatus.CREATED : HttpStatus.CONFLICT;

            // Create a response entity with the appropriate status and body
            PinActivationResponse response = new PinActivationResponse(status);
            return new ResponseEntity<>(response, httpStatus);
        } catch (Exception e) {
            // Handle any exceptions and return an error response
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}