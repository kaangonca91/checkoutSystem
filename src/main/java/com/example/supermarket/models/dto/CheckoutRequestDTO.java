package com.example.supermarket.models.dto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CheckoutRequestDTO extends BaseDTO {

    // Backward compatible input where each code represents one unit.
    private List<String> items;

    // Preferred input style with explicit product quantity.
    @Valid
    private List<CheckoutItemRequestDTO> lines;
}
