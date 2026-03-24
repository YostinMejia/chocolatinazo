package com.yostin.evolucioncb.chocolatinazo.domain.models;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Getter
@Builder
public class Game {
    private String id;
    private LocalDateTime createdAt;
    private BigDecimal chocolatinaPrice;
    private Rule rule;
    private Status status;
    private String adminPassword;
    private String gameCode;

    public enum Status{
        WAITING,
        FINISHED,
        PLAYING;

        public String getValue(){
            return this.name();
        }
    }

    public enum Rule{
        HIGHEST,
        LOWEST;

        public String getValue(){
            return this.name();
        }
    }

}