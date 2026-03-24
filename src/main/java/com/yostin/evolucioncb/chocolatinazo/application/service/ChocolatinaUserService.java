package com.yostin.evolucioncb.chocolatinazo.application.service;

import com.yostin.evolucioncb.chocolatinazo.domain.exceptions.GameException;
import com.yostin.evolucioncb.chocolatinazo.domain.models.ChocolatinaUser;
import com.yostin.evolucioncb.chocolatinazo.domain.repositories.ChocolatinaUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class ChocolatinaUserService {
    private final ChocolatinaUserRepository chocolatinaUserRepository;

    public ChocolatinaUser save(String gameCode, String email) {
        if (hasUserJoined(gameCode, email)){
            throw new GameException("User has already joined this game");
        }

        ChocolatinaUser chocolatinaUser = ChocolatinaUser.builder()
                .stickerNumber(generateUniqueStickerNumber(gameCode))
                .userEmail(email)
                .gameCode(gameCode)
                .createdAt(LocalDateTime.now())
                .build();
        return chocolatinaUserRepository.save(chocolatinaUser);
    }

    private boolean hasUserJoined(String gameCode, String email){
        return chocolatinaUserRepository.existsByGameCodeAndUserEmail(gameCode, email);
    }

    private int generateUniqueStickerNumber(String gameCode) {
        int stickerNumber;
        do {
            stickerNumber = getRandomNumber();
        } while (chocolatinaUserRepository.existsByStickerNumberAndGameCode(stickerNumber, gameCode));

        return stickerNumber;
    }

    private static int getRandomNumber() {
        int min = 1;
        int max = 320;
        return min + (int) (Math.random() * ((max - min) + 1));
    }
}
