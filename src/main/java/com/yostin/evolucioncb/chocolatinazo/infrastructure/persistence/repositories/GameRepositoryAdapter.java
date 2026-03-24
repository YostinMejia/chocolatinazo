package com.yostin.evolucioncb.chocolatinazo.infrastructure.persistence.repositories;

import com.yostin.evolucioncb.chocolatinazo.domain.models.Game;
import com.yostin.evolucioncb.chocolatinazo.domain.repositories.GameRepository;
import com.yostin.evolucioncb.chocolatinazo.infrastructure.mappers.GameMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class GameRepositoryAdapter implements GameRepository {

    private final JpaGameRepository gameRepository;
    private final GameMapper gameMapper;

    @Override
    public Game save(Game game) {
        return gameMapper.toGameFromEntity(gameRepository.save(gameMapper.toGameEntity(game)));
    }

    @Override
    public boolean existsByGameCode(String gameCode) {
        return gameRepository.existsByGameCode(gameCode);
    }
}
