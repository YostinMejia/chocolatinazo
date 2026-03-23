package com.yostin.evolucioncb.chocolatinazo.infrastructure.entry.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateUserDto (
    @NotNull
    @NotBlank
    @Size(min = 3)
    String name
){}

