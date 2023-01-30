package com.purgenta.gameshop.models;

import com.purgenta.gameshop.validation.ValidateUniqueTitle;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @ValidateUniqueTitle
    @Column(unique = true, nullable = false)
    private String title;

    @NotNull
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;
    @ManyToOne
    private User user;

    @ManyToOne
    private GameCategory category;

    @ManyToOne
    private Publisher publisher;

    private String img_path;

}
