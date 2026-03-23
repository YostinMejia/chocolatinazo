package com.yostin.evolucioncb.chocolatinazo.domain.repositories;

import com.yostin.evolucioncb.chocolatinazo.domain.models.User;

public interface UserRepository {
    User save(User user);
}
