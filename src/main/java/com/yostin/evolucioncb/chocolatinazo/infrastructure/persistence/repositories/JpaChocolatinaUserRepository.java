package com.yostin.evolucioncb.chocolatinazo.infrastructure.persistence.repositories;

import com.yostin.evolucioncb.chocolatinazo.infrastructure.persistence.entities.ChocolatinaUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaChocolatinaUserRepository extends JpaRepository<ChocolatinaUserEntity, String> {
    boolean existsByStickerNumberAndGameCode(int stickerNumber, String gameCode);
    boolean existsByGameCodeAndUserEmail(String gameCode, String userEmail);

}