package com.yostin.evolucioncb.chocolatinazo.application.service;

import com.yostin.evolucioncb.chocolatinazo.domain.game.exceptions.GameException;
import com.yostin.evolucioncb.chocolatinazo.domain.chocolatinauser.models.ChocolatinaUser;
import com.yostin.evolucioncb.chocolatinazo.domain.finishedgame.models.FinishedGame;
import com.yostin.evolucioncb.chocolatinazo.domain.game.models.Game;
import com.yostin.evolucioncb.chocolatinazo.domain.finishedgame.repositories.FinishedGameRepository;
import com.yostin.evolucioncb.chocolatinazo.domain.game.repositories.GameRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final ChocolatinaUserService chocolatinaUserService;
    private final FinishedGameRepository finishedGameRepository;

    public Game save(BigDecimal chocolatinaPrice, Game.Rule rule) {
        Game game = generateGame(chocolatinaPrice, rule);
        return this.gameRepository.save(game);
    }

    public ChocolatinaUser join(String email) {
        return this.chocolatinaUserService.save(email);
    }

    private Game generateGame(BigDecimal chocolatinaPrice, Game.Rule rule) {
        return Game.builder()
                .createdAt(LocalDateTime.now())
                .status(Game.Status.WAITING)
                .rule(rule)
                .chocolatinaPrice(chocolatinaPrice)
                .build();
    }

    public List<ChocolatinaUser> findAll() {
        return chocolatinaUserService.findAll();
    }

    public Game updateChocolatinaPrice(String gameId, BigDecimal newPrice) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameException("Game not found with id: " + gameId));

        Game updatedGame = Game.builder()
                .id(game.getId())
                .createdAt(game.getCreatedAt())
                .chocolatinaPrice(newPrice)
                .rule(game.getRule())
                .status(game.getStatus())
                .build();

        return gameRepository.update(updatedGame);
    }

    @Transactional
    public FinishedGame calculateLoser() {
        Game game = gameRepository.findCurrentGame()
                .orElseThrow(() -> new GameException("No active game found"));

        List<ChocolatinaUser> players = chocolatinaUserService.findAll();
        if (players.isEmpty()) {
            throw new GameException("No players have joined the current game");
        }

        ChocolatinaUser loser = determineLoser(players, game.getRule());
        int totalPlayers = players.size();
        final BigDecimal totalLost = calculateTotalLost(game, totalPlayers);

        final FinishedGame saved = saveFinishedGame(loser, totalPlayers, game, totalLost);

        chocolatinaUserService.deleteAll();
        gameRepository.deleteAll();
        return saved;
    }

    private FinishedGame saveFinishedGame(ChocolatinaUser loser, int totalPlayers, Game game, BigDecimal totalLost) {
        FinishedGame finishedGame = FinishedGame.builder()
                .loserEmail(loser.getUserEmail())
                .losingNumber(loser.getStickerNumber())
                .totalPlayers(totalPlayers)
                .unitPrice(game.getChocolatinaPrice())
                .totalLost(totalLost)
                .finishedAt(LocalDateTime.now())
                .build();

        return finishedGameRepository.save(finishedGame);
    }

    private BigDecimal calculateTotalLost(Game game, int totalPlayers) {
        return game.getChocolatinaPrice()
                .multiply(BigDecimal.valueOf(totalPlayers));
    }

    private ChocolatinaUser determineLoser(List<ChocolatinaUser> players, Game.Rule rule) {
        return switch (rule) {
            case HIGHEST -> players.stream()
                    .max(Comparator.comparingInt(ChocolatinaUser::getStickerNumber))
                    .orElseThrow(() -> new GameException("Could not determine loser"));
            case LOWEST -> players.stream()
                    .min(Comparator.comparingInt(ChocolatinaUser::getStickerNumber))
                    .orElseThrow(() -> new GameException("Could not determine loser"));
        };
    }

}
