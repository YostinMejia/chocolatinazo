package com.yostin.evolucioncb.chocolatinazo.application.service;

import com.yostin.evolucioncb.chocolatinazo.domain.game.exceptions.GameException;
import com.yostin.evolucioncb.chocolatinazo.domain.chocolatinauser.models.ChocolatinaUser;
import com.yostin.evolucioncb.chocolatinazo.domain.chocolatinauser.repositories.ChocolatinaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Service
public class ChocolatinaUserService {
    private final ChocolatinaUserRepository chocolatinaUserRepository;

    public ChocolatinaUser save(String email) {
        if (hasUserJoined(email)) {
            throw new GameException("User has already joined this game");
        }

        ChocolatinaUser chocolatinaUser = ChocolatinaUser.builder()
                .stickerNumber(generateUniqueStickerNumber())
                .userEmail(email)
                .createdAt(LocalDateTime.now())
                .build();
        return chocolatinaUserRepository.save(chocolatinaUser);
    }

    private boolean hasUserJoined(String email) {
        return chocolatinaUserRepository.existsByUserEmail(email);
    }

    private int generateUniqueStickerNumber() {
        List<Integer> takenNumbers = chocolatinaUserRepository.findAllStickerNumbers();

        List<Integer> allPossibleNumbers = IntStream.rangeClosed(1, 320)
                .boxed()
                .collect(Collectors.toList());

        allPossibleNumbers.removeAll(takenNumbers);

        if (allPossibleNumbers.isEmpty()) {
            throw new RuntimeException("No more chocolatinas available!");
        }

        Collections.shuffle(allPossibleNumbers);
        return allPossibleNumbers.getFirst();
    }

    public List<ChocolatinaUser> findAll(){
        return chocolatinaUserRepository.findAll();
    }

    public void deleteAll(){
        chocolatinaUserRepository.deleteAll();
    }
    
}
