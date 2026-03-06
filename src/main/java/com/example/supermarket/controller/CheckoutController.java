package com.example.supermarket.controller;

import com.example.supermarket.models.dto.CheckoutRequestDTO;
import com.example.supermarket.models.dto.CheckoutResultDTO;
import com.example.supermarket.services.CheckoutService;
import com.example.supermarket.util.CheckoutRequestMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("${checkout.request.mapping}")
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping
    public CheckoutResultDTO checkout(@Valid @RequestBody CheckoutRequestDTO request) {
        Map<String, Long> requestedQuantities = CheckoutRequestMapper.toRequestedQuantities(request);
        if (requestedQuantities.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request must contain items or lines");
        }
        return checkoutService.checkout(requestedQuantities);
    }
}
