package com.purgenta.gameshop.repositories;

import com.purgenta.gameshop.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<UserModel, Integer> {
    UserModel findByEmail(String email);
}
