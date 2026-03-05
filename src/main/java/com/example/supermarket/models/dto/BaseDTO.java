package com.example.supermarket.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class BaseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
}
