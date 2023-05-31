package com.purgenta.gameshop.models.cart;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @ManyToOne
    private User user;

    @JsonIgnore
    @OneToOne(mappedBy = "cart")
    private Order order;
    @OneToMany(mappedBy = "cart")
    private List<CartItem> cartItems;

    @JsonIgnore
    @Enumerated
    private CartStatus cartStatus;
}

