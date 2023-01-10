package com.purgenta.gameshop.services;

import com.purgenta.gameshop.models.User;

import java.util.List;
import java.util.Map;

public interface IUserService {

    User getAuthenticatedUser();

    Map<String, String> createUser(User user);

    User findByEmail(String email);

    List<User> getUsers();

}
