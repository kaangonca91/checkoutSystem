package com.example.supermarket.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CheckoutItemRequestDTO extends BaseDTO {

    @NotBlank(message = "productCode must be provided")
    private String productCode;

    @NotNull(message = "quantity must be provided")
    @Positive(message = "quantity must be greater than 0")
    private Long quantity;
}
