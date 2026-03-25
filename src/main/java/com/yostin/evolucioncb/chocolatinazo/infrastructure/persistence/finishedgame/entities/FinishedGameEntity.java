package com.yostin.evolucioncb.chocolatinazo.infrastructure.persistence.finishedgame.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "finished_game")
@Data
public class FinishedGameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "loser_email", nullable = false)
    private String loserEmail;

    @Column(name = "losing_number", nullable = false)
    private int losingNumber;

    @Column(name = "total_players", nullable = false)
    private int totalPlayers;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "total_lost", nullable = false)
    private BigDecimal totalLost;

    @Column(name = "finished_at", nullable = false)
    private LocalDateTime finishedAt;
}