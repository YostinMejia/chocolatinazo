package com.yostin.evolucioncb.chocolatinazo.application.service;

import com.yostin.evolucioncb.chocolatinazo.domain.models.Game;
import com.yostin.evolucioncb.chocolatinazo.domain.repositories.GameRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class GameService {
    private final GameRepository gameRepository;

    public Game save(BigDecimal chocolatinaPrice, Game.Rule rule, String adminPassword) {
        Game game = generateGame(chocolatinaPrice, rule, adminPassword);
        return this.gameRepository.save(game);
    }

    private Game generateGame(BigDecimal chocolatinaPrice, Game.Rule rule, String adminPassword) {
        return Game.builder()
                .gameCode(UUID.randomUUID().toString())
                .createdAt(LocalDateTime.now())
                .status(Game.Status.WAITING)
                .rule(rule)
                .chocolatinaPrice(chocolatinaPrice)
                .adminPassword(adminPassword)
                .build();
    }
}
