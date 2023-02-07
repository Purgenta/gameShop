package com.purgenta.gameshop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.purgenta.gameshop.validation.publisher.ValidateUniquePublisher;
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
    @ValidateUniquePublisher
    @Column(nullable = false,unique = true)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "publisher")
    private List<Game> publishedGames;
}
