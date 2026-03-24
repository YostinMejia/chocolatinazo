package com.yostin.evolucioncb.chocolatinazo.domain.repositories;

import com.yostin.evolucioncb.chocolatinazo.domain.dto.AuthResult;
import com.yostin.evolucioncb.chocolatinazo.domain.models.Roles;

public interface AuthRepository {
    void signUp(String email, String password, String username, Roles role);
    AuthResult login(String email, String password);
    void confirmSignUp(String email, String confirmationCode);
}
