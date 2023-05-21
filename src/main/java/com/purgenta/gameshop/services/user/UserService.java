package com.purgenta.gameshop.services.user;

import com.purgenta.gameshop.models.user.User;
import com.purgenta.gameshop.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service()
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository userRepository;

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return findByEmail(authentication.getName());
    }

    @Override
    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }


    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }


}
