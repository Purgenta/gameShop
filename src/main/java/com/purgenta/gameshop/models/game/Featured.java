package com.purgenta.gameshop.models.game;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Featured {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    private Game game;
    private String banner;
    private String text;
}
