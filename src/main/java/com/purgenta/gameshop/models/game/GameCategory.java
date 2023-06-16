package com.purgenta.gameshop.models.game;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class GameCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Size(min = 5, max = 20)
    @Column(unique = true, nullable = false)
    private String name;
    @NotNull
    @Size(min=20, max=120)
    @Column(nullable = false,columnDefinition = "TEXT")
    private String description;
}
