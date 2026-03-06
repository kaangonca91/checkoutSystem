package com.example.supermarket.services;

import com.example.supermarket.exceptions.UnknownProductException;
import com.example.supermarket.models.Product;
import com.example.supermarket.models.WeeklyOffer;
import com.example.supermarket.models.dto.CheckoutLineDTO;
import com.example.supermarket.models.dto.CheckoutResultDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class CheckoutService {

    private static final String CURRENCY_EUR = "EUR";

    private final ProductCatalogService productCatalogService;
    private final WeeklyOfferService weeklyOfferService;

    public CheckoutService(ProductCatalogService productCatalogService, WeeklyOfferService weeklyOfferService) {
        this.productCatalogService = productCatalogService;
        this.weeklyOfferService = weeklyOfferService;
    }

    public CheckoutResultDTO checkout(List<String> itemCodes) {
        List<String> safeItems = itemCodes == null ? Collections.emptyList() : itemCodes;
        Map<String, Long> quantitiesByCode = safeItems.stream()
                .map(this::normalize)
                .collect(Collectors.groupingBy(code -> code, TreeMap::new, Collectors.counting()));

        List<CheckoutLineDTO> lines = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (Map.Entry<String, Long> entry : quantitiesByCode.entrySet()) {
            String code = entry.getKey();
            long quantity = entry.getValue();
            Product product = productCatalogService.findByCode(code).orElseThrow(() -> new UnknownProductException(code));
            WeeklyOffer offer = weeklyOfferService.findByProductCode(code).orElse(null);

            long offerBundleCount = 0L;
            BigDecimal lineTotal;

            if (offer != null && offer.getRequiredQuantity() > 0) {
                offerBundleCount = quantity / offer.getRequiredQuantity();
                long remaining = quantity % offer.getRequiredQuantity();
                lineTotal = offer.getOfferPrice().multiply(BigDecimal.valueOf(offerBundleCount))
                        .add(product.getUnitPrice().multiply(BigDecimal.valueOf(remaining)));
            } else {
                lineTotal = product.getUnitPrice().multiply(BigDecimal.valueOf(quantity));
            }

            lineTotal = scale(lineTotal);
            total = total.add(lineTotal);

            lines.add(CheckoutLineDTO.builder()
                    .productCode(product.getCode())
                    .productName(product.getName())
                    .quantity(quantity)
                    .unitPrice(scale(product.getUnitPrice()))
                    .offerBundleCount(offerBundleCount > 0 ? offerBundleCount : null)
                    .offerQuantity(offerBundleCount > 0 ? offer.getRequiredQuantity() : null)
                    .offerPrice(offerBundleCount > 0 ? scale(offer.getOfferPrice()) : null)
                    .lineTotal(lineTotal)
                    .build());
        }

        return CheckoutResultDTO.builder()
                .lines(lines)
                .total(scale(total))
                .currency(CURRENCY_EUR)
                .build();
    }

    private String normalize(String code) {
        if (code == null || code.isBlank()) {
            throw new UnknownProductException(String.valueOf(code));
        }
        return code.trim().toUpperCase(Locale.ROOT);
    }

    private BigDecimal scale(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP);
    }
}
