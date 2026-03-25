package com.yostin.evolucioncb.chocolatinazo.domain.auth.repositories;

import com.yostin.evolucioncb.chocolatinazo.domain.auth.dto.AuthResult;
import com.yostin.evolucioncb.chocolatinazo.domain.auth.models.Roles;

public interface AuthRepository {
    void signUp(String email, String password, String username, Roles role);
    AuthResult login(String email, String password);
    void confirmSignUp(String email, String confirmationCode);
}
