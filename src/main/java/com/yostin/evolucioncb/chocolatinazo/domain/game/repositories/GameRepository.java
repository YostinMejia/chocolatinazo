package com.yostin.evolucioncb.chocolatinazo.domain.game.repositories;

import com.yostin.evolucioncb.chocolatinazo.domain.game.models.Game;

import java.util.Optional;

public interface GameRepository {
    Game save(Game game);
    Optional<Game> findById(String id);
    Game update(Game game);
    Optional<Game> findCurrentGame();
    void deleteAll();
}
