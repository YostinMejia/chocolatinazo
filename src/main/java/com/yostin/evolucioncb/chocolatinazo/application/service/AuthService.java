package com.yostin.evolucioncb.chocolatinazo.application.service;

import com.yostin.evolucioncb.chocolatinazo.domain.repositories.AuthRepository;
import com.yostin.evolucioncb.chocolatinazo.dto.SignUpDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;
    public void signUp(SignUpDto signUpDto){
        authRepository.signUp(signUpDto);
    }
}
