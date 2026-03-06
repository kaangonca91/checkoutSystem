package com.example.supermarket.controller;

import com.example.supermarket.models.dto.CheckoutResultDTO;
import com.example.supermarket.services.CartService;
import com.example.supermarket.services.CheckoutService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("${checkout.request.mapping}")
public class CheckoutController {

    private final CheckoutService checkoutService;
    private final CartService cartService;

    public CheckoutController(CheckoutService checkoutService, CartService cartService) {
        this.checkoutService = checkoutService;
        this.cartService = cartService;
    }

    @GetMapping("/{cartId}")
    public CheckoutResultDTO checkout(@PathVariable String cartId) {
        Map<String, Long> requestedQuantities = cartService.getCartQuantities(cartId);
        return checkoutService.checkout(requestedQuantities);
    }
}
