package com.yostin.evolucioncb.chocolatinazo.infrastructure.persistence.repositories;

import com.yostin.evolucioncb.chocolatinazo.domain.models.ChocolatinaUser;
import com.yostin.evolucioncb.chocolatinazo.domain.repositories.ChocolatinaUserRepository;
import com.yostin.evolucioncb.chocolatinazo.infrastructure.mappers.ChocolatinaUserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ChocolatinaUserRepositoryAdapter implements ChocolatinaUserRepository {
    private final JpaChocolatinaUserRepository chocolatinaUserRepository;
    private final ChocolatinaUserMapper chocolatinaUserMapper;

    @Override
    public ChocolatinaUser save(ChocolatinaUser chocolatinaUser) {
        return chocolatinaUserMapper.toModelFromEntity(
                chocolatinaUserRepository.save(chocolatinaUserMapper.toEntityFromModel(chocolatinaUser))
        );
    }

    @Override
    public boolean existsByStickerNumberAndGameCode(int stickerNumber, String gameCode) {
        return chocolatinaUserRepository.existsByStickerNumberAndGameCode(stickerNumber, gameCode);
    }

    @Override
    public boolean existsByGameCodeAndUserEmail(String gameCode, String userEmail) {
        return chocolatinaUserRepository.existsByGameCodeAndUserEmail(gameCode,userEmail);
    }
}
