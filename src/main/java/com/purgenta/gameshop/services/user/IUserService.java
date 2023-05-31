package com.purgenta.gameshop.services.user;

import com.purgenta.gameshop.models.user.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    User getAuthenticatedUser();

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    List<User> getUsers();

}
