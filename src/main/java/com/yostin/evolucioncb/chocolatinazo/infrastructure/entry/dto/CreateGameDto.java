package com.yostin.evolucioncb.chocolatinazo.infrastructure.entry.dto;


import com.yostin.evolucioncb.chocolatinazo.domain.models.Game;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreateGameDto(
        @NotNull
        @DecimalMin(value = "0.0", message = "Price must be greater than 0")
        BigDecimal unitPrice,
        @NotBlank
        Game.Rule rule,
        String adminPassword
) {
}
