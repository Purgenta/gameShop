package com.purgenta.gameshop.models.cart;

import com.purgenta.gameshop.models.order.Order;
import com.purgenta.gameshop.models.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private User user;

    @OneToOne(mappedBy = "cart")
    private Order order;
    @OneToMany(mappedBy = "cart")
    private List<CartItem> cartItems;


    @Enumerated
    private CartStatus cartStatus;
}

