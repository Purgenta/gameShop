package com.purgenta.gameshop.models.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "token")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String token;

    public Token(String token, User user, boolean isRevoked) {
        this.token = token;
        this.user = user;
        this.isRevoked = isRevoked;
    }

    @ManyToOne
    private User user;

    private boolean isRevoked;
}
