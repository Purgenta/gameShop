package com.purgenta.gameshop.models.order;

import com.purgenta.gameshop.models.cart.Cart;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Date issued_at;
    @OneToOne
    private Cart cart;
    private OrderStatus orderStatus;
}
