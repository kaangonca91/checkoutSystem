package com.example.supermarket.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CheckoutLineDTO extends BaseDTO {

    private String productCode;
    private String productName;
    private long quantity;
    private BigDecimal unitPrice;
    private Long offerBundleCount;
    private Integer offerQuantity;
    private BigDecimal offerPrice;
    private BigDecimal lineTotal;
}
