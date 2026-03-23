package com.yostin.evolucioncb.chocolatinazo.infrastructure.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginDto (
        @NotNull
        @NotBlank
        @Email
        String email ,
        @NotNull
        @NotBlank
        String password
){
}
