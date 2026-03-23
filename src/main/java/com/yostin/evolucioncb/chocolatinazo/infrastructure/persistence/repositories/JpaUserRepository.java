package com.yostin.evolucioncb.chocolatinazo.infrastructure.persistence.repositories;

import com.yostin.evolucioncb.chocolatinazo.infrastructure.persistence.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface JpaUserRepository extends CrudRepository<UserEntity, String> {
}
