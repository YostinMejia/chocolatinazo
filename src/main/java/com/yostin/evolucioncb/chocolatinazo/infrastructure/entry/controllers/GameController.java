package com.yostin.evolucioncb.chocolatinazo.infrastructure.entry.controllers;

import com.yostin.evolucioncb.chocolatinazo.application.service.GameService;
import com.yostin.evolucioncb.chocolatinazo.domain.models.ChocolatinaUser;
import com.yostin.evolucioncb.chocolatinazo.domain.models.Game;
import com.yostin.evolucioncb.chocolatinazo.infrastructure.entry.dto.CreateGameDto;
import com.yostin.evolucioncb.chocolatinazo.infrastructure.entry.dto.GameResponseDto;
import com.yostin.evolucioncb.chocolatinazo.infrastructure.entry.dto.UpdateChocolatinaPriceDto;
import com.yostin.evolucioncb.chocolatinazo.infrastructure.mappers.GameMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/game")
@RestController
@AllArgsConstructor
public class GameController {

    private final GameService gameService;
    private final GameMapper gameMapper;

    @PostMapping("/create")
    public ResponseEntity<GameResponseDto> create(@Valid @RequestBody CreateGameDto createGameDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                gameMapper.toResponseFromGame(
                        gameService.save(createGameDto.unitPrice(), createGameDto.rule())
                )
        );
    }

    @PostMapping("/join")
    public ResponseEntity<String> join(@AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getClaim("email");
        if (email == null) {
            return ResponseEntity.badRequest().body("Email claim not found in token");
        }

        ChocolatinaUser chocolatinaUser = gameService.join(email);
        return ResponseEntity.ok().body(email + " Joined successfully to the Game" + " and the sticker is " + chocolatinaUser.getStickerNumber());
    }

    @GetMapping("/audit")
    public ResponseEntity<List<ChocolatinaUser>> findAll() {
        return ResponseEntity.ok().body(gameService.findAll());
    }

    @PatchMapping("/admin/update-price/{gameId}")
    public ResponseEntity<Game> updateChocolatinaPrice(
            @PathVariable String gameId, @Valid @RequestBody UpdateChocolatinaPriceDto request) {

        return ResponseEntity.ok().body(gameService.updateChocolatinaPrice(
                gameId,
                request.chocolatinaPrice()
        ));
    }
}
