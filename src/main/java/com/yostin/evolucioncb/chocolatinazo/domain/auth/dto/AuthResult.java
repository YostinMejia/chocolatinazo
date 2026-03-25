package com.yostin.evolucioncb.chocolatinazo.domain.auth.dto;

public record AuthResult(String idToken, String accessToken, String refreshToken) {}
