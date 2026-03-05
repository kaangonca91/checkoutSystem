package com.example.supermarket.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyOffer {

    private String productCode;
    private int requiredQuantity;
    private BigDecimal offerPrice;
}
