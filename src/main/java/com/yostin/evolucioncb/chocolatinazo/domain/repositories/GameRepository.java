package com.yostin.evolucioncb.chocolatinazo.domain.repositories;

import com.yostin.evolucioncb.chocolatinazo.domain.models.Game;

public interface GameRepository {
    Game save(Game game);
}
