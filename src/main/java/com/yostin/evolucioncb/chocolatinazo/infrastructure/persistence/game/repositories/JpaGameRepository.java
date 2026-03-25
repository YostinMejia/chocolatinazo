package com.yostin.evolucioncb.chocolatinazo.infrastructure.persistence.game.repositories;

import com.yostin.evolucioncb.chocolatinazo.domain.game.models.Game;
import com.yostin.evolucioncb.chocolatinazo.infrastructure.persistence.game.entities.GameEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface JpaGameRepository extends CrudRepository<GameEntity,String> {
    Optional<GameEntity> findFirstByStatusIn(List<Game.Status> statuses);

}
