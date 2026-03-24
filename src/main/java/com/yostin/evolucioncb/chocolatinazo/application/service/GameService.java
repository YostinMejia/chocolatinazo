package com.yostin.evolucioncb.chocolatinazo.application.service;

import com.yostin.evolucioncb.chocolatinazo.domain.exceptions.GameException;
import com.yostin.evolucioncb.chocolatinazo.domain.models.ChocolatinaUser;
import com.yostin.evolucioncb.chocolatinazo.domain.models.Game;
import com.yostin.evolucioncb.chocolatinazo.domain.repositories.GameRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final ChocolatinaUserService chocolatinaUserService;

    public Game save(BigDecimal chocolatinaPrice, Game.Rule rule) {
        Game game = generateGame(chocolatinaPrice, rule);
        return this.gameRepository.save(game);
    }

    public ChocolatinaUser join(String email) {
//        if (!chocolatinaUserService.(gameCode)){
//            throw new GameException("Invalid Game Code");
//        }
        return this.chocolatinaUserService.save(email);
    }

//    public boolean isValidAdminPassword(Game game, String passwordGiven) {
//        return game.getAdminPassword().equals(passwordGiven);
//  }

    private Game generateGame(BigDecimal chocolatinaPrice, Game.Rule rule) {
        return Game.builder()
                .createdAt(LocalDateTime.now())
                .status(Game.Status.WAITING)
                .rule(rule)
                .chocolatinaPrice(chocolatinaPrice)
                .build();
    }
}
