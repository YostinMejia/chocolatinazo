package com.yostin.evolucioncb.chocolatinazo.domain.auth.models;

public enum Roles {
    ADMIN,
    AUDITOR,
    PLAYER;

    public String getValue() {
        return this.name();
    }
}
