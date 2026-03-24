package com.yostin.evolucioncb.chocolatinazo.application.service;

import com.yostin.evolucioncb.chocolatinazo.domain.dto.AuthResult;
import com.yostin.evolucioncb.chocolatinazo.domain.repositories.AuthRepository;
import com.yostin.evolucioncb.chocolatinazo.infrastructure.auth.dto.ConfirmEmailDto;
import com.yostin.evolucioncb.chocolatinazo.infrastructure.auth.dto.LoginDto;
import com.yostin.evolucioncb.chocolatinazo.infrastructure.auth.dto.SignUpDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;
    private final GameService gameService;
    public void signUp(SignUpDto signUpDto){
        authRepository.signUp(signUpDto.email(),signUpDto.password(), signUpDto.username(), signUpDto.role());
    }

    public AuthResult login(LoginDto loginDto){
        return authRepository.login(loginDto.email(), loginDto.password());
    }

    public void confirmSignup(ConfirmEmailDto confirmEmailDto){
        authRepository.confirmSignUp(confirmEmailDto.email(), confirmEmailDto.confirmationCode());
    }
}
