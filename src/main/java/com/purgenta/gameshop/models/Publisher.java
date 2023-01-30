package com.purgenta.gameshop.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int publisher_id;

    @NotNull
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "publisher")
    private List<Game> publishedGames;
}
