package com.yostin.evolucioncb.chocolatinazo.infrastructure.persistence.chocolatinauser.repositories;

import com.yostin.evolucioncb.chocolatinazo.infrastructure.persistence.chocolatinauser.entities.ChocolatinaUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaChocolatinaUserRepository extends JpaRepository<ChocolatinaUserEntity, String> {
    @Query("SELECT c.stickerNumber FROM ChocolatinaUserEntity c")
    List<Integer> findAllStickerNumbers();
    boolean existsByUserEmail(String email);
}