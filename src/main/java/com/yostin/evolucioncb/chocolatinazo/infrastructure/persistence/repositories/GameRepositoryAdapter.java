package com.yostin.evolucioncb.chocolatinazo.infrastructure.persistence.repositories;

import com.yostin.evolucioncb.chocolatinazo.domain.models.Game;
import com.yostin.evolucioncb.chocolatinazo.domain.repositories.GameRepository;
import com.yostin.evolucioncb.chocolatinazo.infrastructure.mappers.GameMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GameRepositoryAdapter implements GameRepository {

    private final JpaGameRepository gameRepository;
    private final GameMapper gameMapper;

    @Override
    public Game save(Game game) {
        return gameMapper.toGameFromEntity(gameRepository.save(gameMapper.toGameEntity(game)));
    }

    @Override
    public Optional<Game> findById(String id) {
        return gameRepository.findById(id)
                .map(gameMapper::toGameFromEntity);
    }

    @Override
    public Game update(Game game) {
        return gameMapper.toGameFromEntity(
                gameRepository.save(gameMapper.toGameEntity(game))
        );
    }
}
