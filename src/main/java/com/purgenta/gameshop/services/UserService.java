package com.purgenta.gameshop.services;

import com.purgenta.gameshop.models.User;
import com.purgenta.gameshop.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service()
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository userRepository;

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return findByEmail(authentication.getName());
    }

    @Override
    public Map<String, String> createUser(User user) {
        userRepository.save(user);
        Map<String, String> response = new HashMap<>();
        response.put("success", "User successfully created");
        return response;
    }

    @Override
    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }


}
