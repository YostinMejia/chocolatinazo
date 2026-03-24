package com.yostin.evolucioncb.chocolatinazo.domain.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
public class ChocolatinaUser {
    private String id;
    private String userEmail;
    private int stickerNumber;
    private LocalDateTime createdAt;
}