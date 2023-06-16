package com.purgenta.gameshop.models.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.purgenta.gameshop.models.cart.Cart;
import com.purgenta.gameshop.models.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Date issued_at;
    @OneToOne
    private Cart cart;
    @OneToOne(mappedBy = "order")
    private OrderReview orderReview;
    @JsonIgnore
    @ManyToOne
    private User user;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}
