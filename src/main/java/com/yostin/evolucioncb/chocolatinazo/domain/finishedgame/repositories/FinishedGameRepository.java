package com.yostin.evolucioncb.chocolatinazo.domain.finishedgame.repositories;

import com.yostin.evolucioncb.chocolatinazo.domain.finishedgame.models.FinishedGame;

import java.util.List;

public interface FinishedGameRepository {
    FinishedGame save(FinishedGame finishedGame);
    List<FinishedGame> findAll();

}
