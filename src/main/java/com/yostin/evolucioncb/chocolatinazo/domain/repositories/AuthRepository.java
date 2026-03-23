package com.yostin.evolucioncb.chocolatinazo.domain.repositories;

import com.yostin.evolucioncb.chocolatinazo.domain.dto.AuthResult;

public interface AuthRepository {
    void signUp(String email, String password, String username, String role);
    AuthResult login(String email, String password);
    void confirmSignUp(String email, String confirmationCode);
}
