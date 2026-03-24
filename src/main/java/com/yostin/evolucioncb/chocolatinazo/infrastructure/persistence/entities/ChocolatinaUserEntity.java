package com.yostin.evolucioncb.chocolatinazo.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "chocolatina_user")
@Data
public class ChocolatinaUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private int stickerNumber;

//    @Column(nullable = false)
//    private String gameCode;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}