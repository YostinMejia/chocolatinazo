package com.yostin.evolucioncb.chocolatinazo.infrastructure.entry.dto;


import com.yostin.evolucioncb.chocolatinazo.domain.models.Game;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreateGameDto(
        @NotNull
        @DecimalMin(value = "0.0", message = "Price must be greater than 0")
        BigDecimal unitPrice,
        @NotNull(message = "Rule must be either HIGHEST or LOWEST")
        Game.Rule rule
) {
}
