package com.yostin.evolucioncb.chocolatinazo.infrastructure.persistence.repositories;

import com.yostin.evolucioncb.chocolatinazo.infrastructure.persistence.entities.GameEntity;
import org.springframework.data.repository.CrudRepository;

public interface JpaGameRepository extends CrudRepository<GameEntity,String> {
    boolean existsByGameCode(String gameCode);
}
