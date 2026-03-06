package com.example.supermarket.exceptions;

public class CartNotFoundException extends RuntimeException {

    public CartNotFoundException(String cartId) {
        super("Cart not found: " + cartId);
    }
}
