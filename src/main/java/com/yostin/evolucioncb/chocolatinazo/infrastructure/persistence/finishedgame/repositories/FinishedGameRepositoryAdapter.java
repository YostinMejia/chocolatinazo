package com.yostin.evolucioncb.chocolatinazo.infrastructure.persistence.finishedgame.repositories;

import com.yostin.evolucioncb.chocolatinazo.domain.finishedgame.models.FinishedGame;
import com.yostin.evolucioncb.chocolatinazo.domain.finishedgame.repositories.FinishedGameRepository;
import com.yostin.evolucioncb.chocolatinazo.infrastructure.mappers.FinishedGameMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class FinishedGameRepositoryAdapter implements FinishedGameRepository {

    private final JpaFinishedGameRepository finishedGameRepository;
    private final FinishedGameMapper finishedGameMapper;

    @Override
    public FinishedGame save(FinishedGame finishedGame) {
        return finishedGameMapper.toModelFromEntity(
                finishedGameRepository.save(
                        finishedGameMapper.toEntityFromModel(finishedGame)
                )
        );
    }

    @Override
    public List<FinishedGame> findAll() {
        return finishedGameRepository.findAll().stream().map(finishedGameMapper::toModelFromEntity).toList();
    }

}