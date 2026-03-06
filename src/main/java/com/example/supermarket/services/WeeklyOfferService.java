package com.example.supermarket.services;

import com.example.supermarket.models.WeeklyOffer;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@Service
public class WeeklyOfferService {

    private final Map<String, WeeklyOffer> offersByProductCode = Map.of(
            "APPLE", WeeklyOffer.builder()
                    .productCode("APPLE")
                    .requiredQuantity(2)
                    .offerPrice(price("0.45"))
                    .build()
    );

    public Optional<WeeklyOffer> findByProductCode(String productCode) {
        if (productCode == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(offersByProductCode.get(normalize(productCode)));
    }

    private static String normalize(String code) {
        return code.trim().toUpperCase(Locale.ROOT);
    }

    private static BigDecimal price(String value) {
        return new BigDecimal(value);
    }
}
