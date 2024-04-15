package com.example.pinactivator.service;

import com.example.pinactivator.model.ActivationStatus;
import com.example.pinactivator.model.PinActivationRequest;
import org.springframework.stereotype.Service;

@Service
public class Orchestrator {
    private PinActivationService pinActivationService;

    public Orchestrator(PinActivationService pinActivationService) {
        this.pinActivationService = pinActivationService;
    }

    public ActivationStatus activatePin(PinActivationRequest pinActivationRequest){
       return pinActivationService.activatePinTerminal(pinActivationRequest.getCustomerId(),pinActivationRequest.getMacAddress());
    }
}
