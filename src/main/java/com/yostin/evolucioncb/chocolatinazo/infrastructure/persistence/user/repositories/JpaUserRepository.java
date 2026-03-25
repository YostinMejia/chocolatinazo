package com.yostin.evolucioncb.chocolatinazo.infrastructure.persistence.user.repositories;

import com.yostin.evolucioncb.chocolatinazo.infrastructure.persistence.user.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface JpaUserRepository extends CrudRepository<UserEntity, String> {
}
