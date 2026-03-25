package com.yostin.evolucioncb.chocolatinazo.domain.user.repositories;

import com.yostin.evolucioncb.chocolatinazo.domain.user.models.User;

public interface UserRepository {
    User save(User user);
}
