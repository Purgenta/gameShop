package com.purgenta.gameshop.services.user;

import com.purgenta.gameshop.models.user.User;
import com.purgenta.gameshop.repositories.IOrderRepository;
import com.purgenta.gameshop.repositories.IUserRepository;
import com.purgenta.gameshop.response.user.Stats;
import com.purgenta.gameshop.response.user.UserProfile;
import com.purgenta.gameshop.services.game.IGameService;
import com.purgenta.gameshop.services.order.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service()
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final IGameService iGameService;
    private final IOrderRepository orderRepository;
    private final IOrderService iOrderService;

    public ResponseEntity<UserProfile> getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var user = (User) authentication.getPrincipal();
        var orders = iOrderService.getUserOrders(user);
        return new ResponseEntity<>(UserProfile
                .builder()
                .registered_at(user.getRegistered_at())
                .orders(orders)
                .role(user.getRole().name())
                .email(user.getEmail()).build(), HttpStatus.OK);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public ResponseEntity<?> getStats() {
        return new ResponseEntity<>(Stats.
                builder().
                userCount(getUserCount()).
                gameCount(iGameService.getGameCount()).
                orderCount(iOrderService.getOrderCount())
                .build(), HttpStatus.OK);
    }


    @Override
    public Long getUserCount() {
        return userRepository.count();
    }


}
