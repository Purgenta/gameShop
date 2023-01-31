package com.purgenta.gameshop.models;

import com.purgenta.gameshop.validation.game.ValidateUniqueTitle;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(nullable = false)
    private double price;

    @Column(unique = true, nullable = false)
    @NotNull
    @ValidateUniqueTitle
    private String title;

    @NotNull
    @Size(min = 15,max = 40)
    @Column(nullable = false)
    private String description;
    @ManyToOne
    private User user;

    @ManyToOne
    private GameCategory category;

    @ManyToOne
    private Publisher publisher;

    private int releaseYear;

    private String img_path;

}
