package com.purgenta.gameshop.services;

import com.purgenta.gameshop.models.UserModel;
import com.purgenta.gameshop.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

@Service()
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;

    @Override
    public Map<String, String> createUser(UserModel user) {
        userRepository.save(user);
        Map<String, String> response = new HashMap<>();
        response.put("success", "User successfully created");
        return response;
    }

    @Override
    public List<UserModel> getUsers() {
        return userRepository.findAll();
    }

}
