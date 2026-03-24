package com.yostin.evolucioncb.chocolatinazo.domain.repositories;

import com.yostin.evolucioncb.chocolatinazo.domain.models.ChocolatinaUser;

import java.util.List;

public interface ChocolatinaUserRepository {

    ChocolatinaUser save(ChocolatinaUser chocolatinaUser);
    boolean existsByUserEmail( String email);
    List<Integer> findAllStickerNumbers();

}
