package com.yostin.evolucioncb.chocolatinazo.infrastructure.auth;

import com.yostin.evolucioncb.chocolatinazo.application.service.AuthService;
import com.yostin.evolucioncb.chocolatinazo.dto.SignUpDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody SignUpDto signUpDto) {
        authService.signUp(signUpDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered. Please verify your email.");
    }
}
