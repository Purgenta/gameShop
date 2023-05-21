package com.purgenta.gameshop.models.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.purgenta.gameshop.models.cart.CartItem;
import com.purgenta.gameshop.models.user.User;
import com.purgenta.gameshop.validation.game.ValidateUniqueTitle;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.List;
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
    @Column(nullable = false)
    private boolean isSelling;
    @NotNull
    @Column(nullable = false)
    private double price;

    @Column(unique = true, nullable = false)
    @NotNull
    @ValidateUniqueTitle
    private String title;

    @NotNull
    @Size(min = 15,max = 200)
    @Column(nullable = false)
    private String description;
    @JsonIgnore
    @ManyToOne
    private User user;
    @JsonIgnore
    @ManyToOne
    private GameCategory category;
    @JsonIgnore
    @ManyToOne
    private Publisher publisher;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "game")
    private List<CartItem> cartItems;


    @JsonIgnore
    @OneToMany(mappedBy = "game")
    private List<GameImage> gameImageList;
    private int releaseYear;
}
