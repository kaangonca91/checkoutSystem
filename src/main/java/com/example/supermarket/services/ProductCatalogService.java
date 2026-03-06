package com.example.supermarket.services;

import com.example.supermarket.models.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductCatalogService {

    private final Map<String, Product> productsByCode = Map.of(
            "APPLE", Product.builder().code("APPLE").name("Apple").unitPrice(price("0.30")).build(),
            "BANANA", Product.builder().code("BANANA").name("Banana").unitPrice(price("0.20")).build(),
            "ORANGE", Product.builder().code("ORANGE").name("Orange").unitPrice(price("0.50")).build(),
            "PEAR", Product.builder().code("PEAR").name("Pear").unitPrice(price("0.45")).build(),
            "KIWI", Product.builder().code("KIWI").name("Kiwi").unitPrice(price("0.80")).build(),
            "MILK", Product.builder().code("MILK").name("Milk").unitPrice(price("1.10")).build(),
            "BREAD", Product.builder().code("BREAD").name("Bread").unitPrice(price("1.30")).build(),
            "EKMEK", Product.builder().code("EKMEK").name("Ekmek").unitPrice(price("1.25")).build(),
            "SUCUK", Product.builder().code("SUCUK").name("Sucuk").unitPrice(price("3.90")).build()
    );

    public Optional<Product> findByCode(String code) {
        if (code == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(productsByCode.get(normalize(code)));
    }

    private static String normalize(String code) {
        return code.trim().toUpperCase(Locale.ROOT);
    }

    private static BigDecimal price(String value) {
        return new BigDecimal(value);
    }
}
