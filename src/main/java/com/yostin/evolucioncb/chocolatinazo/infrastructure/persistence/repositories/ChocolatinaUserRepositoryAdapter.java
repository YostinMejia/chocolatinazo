package com.yostin.evolucioncb.chocolatinazo.infrastructure.persistence.repositories;

import com.yostin.evolucioncb.chocolatinazo.domain.models.ChocolatinaUser;
import com.yostin.evolucioncb.chocolatinazo.domain.repositories.ChocolatinaUserRepository;
import com.yostin.evolucioncb.chocolatinazo.infrastructure.mappers.ChocolatinaUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
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
    public boolean existsByUserEmail(String email) {
        return chocolatinaUserRepository.existsByUserEmail(email);
    }

    @Override
    public List<Integer> findAllStickerNumbers() {
        return chocolatinaUserRepository.findAllStickerNumbers();
    }

    @Override
    public List<ChocolatinaUser> findAll() {
        return chocolatinaUserRepository.findAll().stream().map(chocolatinaUserMapper::toModelFromEntity).toList();
    }
}
