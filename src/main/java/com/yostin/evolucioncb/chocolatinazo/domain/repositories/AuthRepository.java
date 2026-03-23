package com.yostin.evolucioncb.chocolatinazo.domain.repositories;

import com.yostin.evolucioncb.chocolatinazo.dto.SignUpDto;

public interface AuthRepository {
    void signUp(SignUpDto signUpDto);
}
