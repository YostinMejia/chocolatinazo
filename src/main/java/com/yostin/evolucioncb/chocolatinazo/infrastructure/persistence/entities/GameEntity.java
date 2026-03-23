package com.yostin.evolucioncb.chocolatinazo.infrastructure.persistence.entities;


import com.yostin.evolucioncb.chocolatinazo.domain.models.Game;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "game")
@Data
public class GameEntity {

    @Id
    @Column(nullable = false, updatable = false)
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

    @Column(nullable = false, unique = true)
    private String adminPassword;

    @Column(nullable = false, unique = true)
    private String gameCode;
}