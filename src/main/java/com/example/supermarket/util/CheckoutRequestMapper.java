package com.example.supermarket.util;

import com.example.supermarket.models.dto.CheckoutItemRequestDTO;
import com.example.supermarket.models.dto.CheckoutRequestDTO;

import java.util.HashMap;
import java.util.Map;

public final class CheckoutRequestMapper {

    private CheckoutRequestMapper() {
        // Utility class
    }

    public static Map<String, Long> toRequestedQuantities(CheckoutRequestDTO request) {
        if (request == null) {
            return Map.of();
        }

        Map<String, Long> quantities = new HashMap<>();

        if (request.getItems() != null) {
            for (String code : request.getItems()) {
                quantities.merge(code, 1L, Long::sum);
            }
        }

        if (request.getLines() != null) {
            for (CheckoutItemRequestDTO line : request.getLines()) {
                quantities.merge(line.getProductCode(), line.getQuantity(), Long::sum);
            }
        }

        return quantities;
    }
}
