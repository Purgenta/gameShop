package com.purgenta.gameshop.models.review;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.purgenta.gameshop.models.game.Game;
import com.purgenta.gameshop.models.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private User user;
    @JsonIgnore
    @ManyToOne
    private Game game;
    private int rating;
    private Date createdAt;
    @Column(columnDefinition = "MEDIUMTEXT")
    private String review;
}
