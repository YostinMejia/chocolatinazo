package com.yostin.evolucioncb.chocolatinazo.domain.game.models;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Getter
@Builder(toBuilder = true)
public class Game {
    private String id;
    private LocalDateTime createdAt;
    private BigDecimal chocolatinaPrice;
    private Rule rule;
    private Status status;

    public enum Status{
        WAITING,
        PLAYING
    }

    public enum Rule{
        HIGHEST,
        LOWEST
    }

}