package com.yostin.evolucioncb.chocolatinazo.domain.finishedgame.models;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@ToString
public class FinishedGame {
    private String id;
    private String loserEmail;
    private int losingNumber;
    private int totalPlayers;
    private BigDecimal unitPrice;
    private BigDecimal totalLost;
    private LocalDateTime finishedAt;
}