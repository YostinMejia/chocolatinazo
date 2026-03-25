package com.yostin.evolucioncb.chocolatinazo.infrastructure.persistence.game.entities;


import com.yostin.evolucioncb.chocolatinazo.domain.game.models.Game;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "game")
@Data
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private BigDecimal chocolatinaPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Game.Rule rule;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Game.Status status;

}