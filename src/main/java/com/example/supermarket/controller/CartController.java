package com.example.supermarket.controller;

import com.example.supermarket.models.dto.CartCreatedResponseDTO;
import com.example.supermarket.models.dto.CheckoutRequestDTO;
import com.example.supermarket.services.CartService;
import com.example.supermarket.util.CheckoutRequestMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("${cart.request.mapping}")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<CartCreatedResponseDTO> createCart(@Valid @RequestBody CheckoutRequestDTO request) {
        Map<String, Long> requestedQuantities = CheckoutRequestMapper.toRequestedQuantities(request);
        String cartId = cartService.createCart(requestedQuantities);
        CartCreatedResponseDTO response = CartCreatedResponseDTO.builder()
                .cartId(cartId)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
