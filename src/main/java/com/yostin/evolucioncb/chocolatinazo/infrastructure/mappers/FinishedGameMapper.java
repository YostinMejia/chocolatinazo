package com.yostin.evolucioncb.chocolatinazo.infrastructure.mappers;

import com.yostin.evolucioncb.chocolatinazo.domain.finishedgame.models.FinishedGame;
import com.yostin.evolucioncb.chocolatinazo.infrastructure.persistence.finishedgame.entities.FinishedGameEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FinishedGameMapper {
    FinishedGameEntity toEntityFromModel(FinishedGame finishedGame);
    FinishedGame toModelFromEntity(FinishedGameEntity entity);
}
