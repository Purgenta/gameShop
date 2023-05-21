package com.purgenta.gameshop.repositories;

import com.purgenta.gameshop.models.user.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ITokenRepository extends JpaRepository<Token,Integer> {
   Optional<Token> findTokenByTokenAndIsRevoked(String token, Boolean revoked);
}
