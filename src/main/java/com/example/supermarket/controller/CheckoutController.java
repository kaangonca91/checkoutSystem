package com.example.supermarket.controller;

import com.example.supermarket.models.dto.CheckoutRequestDTO;
import com.example.supermarket.models.dto.CheckoutResultDTO;
import com.example.supermarket.services.CheckoutService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${checkout.request.mapping}")
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping
    public CheckoutResultDTO checkout(@Valid @RequestBody CheckoutRequestDTO request) {
        return checkoutService.checkout(request == null ? null : request.getItems());
    }
}
