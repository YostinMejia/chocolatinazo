package com.yostin.evolucioncb.chocolatinazo.infrastructure.entry.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record UpdateChocolatinaPriceDto(
        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
        BigDecimal chocolatinaPrice
) {}