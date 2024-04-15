package com.example.pinactivator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PinActivationRequest {
    private String customerId;
    private String macAddress;
}
