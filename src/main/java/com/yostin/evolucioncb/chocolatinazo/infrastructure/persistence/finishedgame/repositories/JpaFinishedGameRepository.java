package com.yostin.evolucioncb.chocolatinazo.infrastructure.persistence.finishedgame.repositories;

import com.yostin.evolucioncb.chocolatinazo.infrastructure.persistence.finishedgame.entities.FinishedGameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaFinishedGameRepository extends JpaRepository<FinishedGameEntity, String> {
}
