package com.yostin.evolucioncb.chocolatinazo.domain.repositories;

import com.yostin.evolucioncb.chocolatinazo.domain.models.ChocolatinaUser;

public interface ChocolatinaUserRepository {

    ChocolatinaUser save(ChocolatinaUser chocolatinaUser);
    boolean existsByStickerNumberAndGameCode(int stickerNumber, String gameCode);
    boolean existsByGameCodeAndUserEmail(String gameCode, String userEmail);

}
