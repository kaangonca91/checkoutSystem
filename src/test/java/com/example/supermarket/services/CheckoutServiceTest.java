package com.example.supermarket.services;

import com.example.supermarket.exceptions.UnknownProductException;
import com.example.supermarket.models.dto.CheckoutResultDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CheckoutServiceTest {

    private final CheckoutService checkoutService = new CheckoutService(
            new ProductCatalogService(),
            new WeeklyOfferService()
    );

    @Test
    void appliesWeeklyOfferForTwoApples() {
        CheckoutResultDTO result = checkoutService.checkout(List.of("apple", "APPLE"));
        assertMoney("0.45", result.getTotal());
    }

    @Test
    void appliesOffersAutomaticallyInMixedCart() {
        CheckoutResultDTO result = checkoutService.checkout(List.of("BANANA", "APPLE", "ORANGE", "APPLE"));
        assertMoney("1.15", result.getTotal());
    }

    @Test
    void appliesMultipleBundlesAndRemainder() {
        CheckoutResultDTO result = checkoutService.checkout(List.of("APPLE", "APPLE", "APPLE", "APPLE", "APPLE"));
        assertMoney("1.20", result.getTotal());
    }

    @Test
    void returnsZeroForEmptyCart() {
        CheckoutResultDTO result = checkoutService.checkout(List.of());
        assertMoney("0.00", result.getTotal());
    }

    @Test
    void throwsForUnknownProduct() {
        assertThrows(UnknownProductException.class, () -> checkoutService.checkout(List.of("PINEAPPLE")));
    }

    @Test
    void supportsExplicitProductQuantityRequest() {
        CheckoutResultDTO result = checkoutService.checkout(Map.of("APPLE", 2L, "ORANGE", 1L));
        assertMoney("0.95", result.getTotal());
    }

    @Test
    void appliesAdditionalWeeklyOffers() {
        CheckoutResultDTO result = checkoutService.checkout(Map.of("KIWI", 2L, "BREAD", 2L));
        assertMoney("3.70", result.getTotal());
    }

    @Test
    void rejectsNonPositiveQuantity() {
        assertThrows(IllegalArgumentException.class, () -> checkoutService.checkout(Map.of("APPLE", 0L)));
    }

    @Test
    void supportsTurkishProductsInCatalog() {
        CheckoutResultDTO result = checkoutService.checkout(Map.of("SUCUK", 1L, "EKMEK", 2L));
        assertMoney("6.40", result.getTotal());
    }

    private static void assertMoney(String expected, BigDecimal actual) {
        assertEquals(0, actual.compareTo(new BigDecimal(expected)));
    }
}
