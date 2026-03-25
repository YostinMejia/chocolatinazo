package com.yostin.evolucioncb.chocolatinazo.infrastructure.mappers;

import com.yostin.evolucioncb.chocolatinazo.domain.game.models.Game;
import com.yostin.evolucioncb.chocolatinazo.infrastructure.persistence.game.entities.GameEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GameMapper {
    GameEntity toGameEntity(Game game);
    Game toGameFromEntity(GameEntity gameEntity);
}
