package com.yostin.evolucioncb.chocolatinazo.infrastructure.entry.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record JoinGameDto(
        @NotNull
        @NotBlank
        String gameCode
        ) {
}
