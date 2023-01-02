package com.purgenta.gameshop.services;

import com.purgenta.gameshop.models.UserModel;

import java.util.Map;
import java.util.List;

public interface IUserService {
    Map<String, String> createUser(UserModel user);

    List<UserModel> getUsers();
}
