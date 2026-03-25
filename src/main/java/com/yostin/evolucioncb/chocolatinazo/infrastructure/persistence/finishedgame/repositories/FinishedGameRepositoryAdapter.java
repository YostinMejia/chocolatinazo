package com.yostin.evolucioncb.chocolatinazo.infrastructure.persistence.finishedgame.repositories;

import com.yostin.evolucioncb.chocolatinazo.domain.finishedgame.models.FinishedGame;
import com.yostin.evolucioncb.chocolatinazo.domain.finishedgame.repositories.FinishedGameRepository;
import com.yostin.evolucioncb.chocolatinazo.infrastructure.mappers.FinishedGameMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class FinishedGameRepositoryAdapter implements FinishedGameRepository {

    private final JpaFinishedGameRepository jpaFinishedGameRepository;
    private final FinishedGameMapper finishedGameMapper;

    @Override
    public FinishedGame save(FinishedGame finishedGame) {
        return finishedGameMapper.toModelFromEntity(
                jpaFinishedGameRepository.save(
                        finishedGameMapper.toEntityFromModel(finishedGame)
                )
        );
    }

}