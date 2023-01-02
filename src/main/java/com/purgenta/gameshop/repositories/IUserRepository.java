package com.purgenta.gameshop.repositories;

import com.purgenta.gameshop.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<UserModel, Integer> {
}
