package com.example.supermarket.services;

import com.example.supermarket.exceptions.CartNotFoundException;
import com.example.supermarket.exceptions.UnknownProductException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CartService {

    private final ProductCatalogService productCatalogService;
    private final Map<String, Map<String, Long>> cartsById = new ConcurrentHashMap<>();

    public CartService(ProductCatalogService productCatalogService) {
        this.productCatalogService = productCatalogService;
    }

    public String createCart(Map<String, Long> requestedQuantities) {
        Map<String, Long> normalized = normalizeAndValidate(requestedQuantities);
        if (normalized.isEmpty()) {
            throw new IllegalArgumentException("Request must contain items or lines");
        }

        String cartId = UUID.randomUUID().toString();
        cartsById.put(cartId, new HashMap<>(normalized));
        return cartId;
    }

    public Map<String, Long> getCartQuantities(String cartId) {
        if (cartId == null || cartId.isBlank()) {
            throw new IllegalArgumentException("cartId must be provided");
        }
        Map<String, Long> quantities = cartsById.get(cartId);
        if (quantities == null) {
            throw new CartNotFoundException(cartId);
        }
        return Map.copyOf(quantities);
    }

    private Map<String, Long> normalizeAndValidate(Map<String, Long> requestedQuantities) {
        if (requestedQuantities == null) {
            return Map.of();
        }

        Map<String, Long> normalized = new HashMap<>();
        for (Map.Entry<String, Long> entry : requestedQuantities.entrySet()) {
            String code = normalizeCode(entry.getKey());
            Long quantity = entry.getValue();
            if (quantity == null || quantity <= 0L) {
                throw new IllegalArgumentException("quantity must be greater than 0 for product: " + code);
            }
            if (productCatalogService.findByCode(code).isEmpty()) {
                throw new UnknownProductException(code);
            }
            normalized.merge(code, quantity, Long::sum);
        }
        return normalized;
    }

    private static String normalizeCode(String code) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("productCode must be provided");
        }
        return code.trim().toUpperCase(Locale.ROOT);
    }
}
