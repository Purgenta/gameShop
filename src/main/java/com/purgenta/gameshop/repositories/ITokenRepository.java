package com.purgenta.gameshop.repositories;

import com.purgenta.gameshop.models.user.Token;
import com.purgenta.gameshop.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ITokenRepository extends JpaRepository<Token,Integer> {
   Optional<Token> findTokenByTokenAndRevoked(String token, Boolean revoked);
   List<Token> findTokenByUserAndRevokedIsFalse(User user);
}
