package com.yostin.evolucioncb.chocolatinazo.infrastructure.auth.dto;

import com.yostin.evolucioncb.chocolatinazo.domain.models.Roles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record SignUpDto (
        @NotNull
        @NotBlank
        @Email
        String email ,
        @NotNull
        @NotBlank
        @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-.]).{8,}$")
        String password,
        @NotNull
        @NotBlank
        String username,
        @NotNull(message = "Role must be PLAYER, AUDITOR or ADMIN")
        Roles role
//        @NotBlank
//        String adminPassword
){
}
