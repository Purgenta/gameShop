package com.purgenta.gameshop.models.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.purgenta.gameshop.models.cart.CartItem;
import com.purgenta.gameshop.models.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @JsonIgnore
    @Column(nullable = false)
    private boolean selling;
    @NotNull
    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    @NotNull
    private String title;

    @NotNull
    @Size(min = 15,max = 500)
    @Column(nullable = false)
    private String description;
    @JsonIgnore
    @ManyToOne
    private User user;

    @ManyToOne
    private GameCategory category;

    @ManyToOne
    private Publisher publisher;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "game")
    private List<CartItem> cartItems;



    @OneToMany(fetch = FetchType.EAGER,mappedBy = "game")
    private List<GameImage> gameImages;
    private int releaseYear;
}
