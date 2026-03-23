package com.yostin.evolucioncb.chocolatinazo.infrastructure.mappers;

import com.yostin.evolucioncb.chocolatinazo.domain.models.Game;
import com.yostin.evolucioncb.chocolatinazo.infrastructure.entry.dto.CreateGameDto;
import com.yostin.evolucioncb.chocolatinazo.infrastructure.entry.dto.GameResponseDto;
import com.yostin.evolucioncb.chocolatinazo.infrastructure.persistence.entities.GameEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GameMapper {
    GameEntity toGameEntity(Game game);
    Game toGameFromEntity(GameEntity gameEntity);
    Game toGameFromCreateDto(CreateGameDto createGameDto);
    GameResponseDto toResponseFromGame(Game game);
}
