package com.purgenta.gameshop.response.user;

import com.purgenta.gameshop.models.order.Order;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
@Builder
public class UserProfile {
    private String email;

    private String role;
    private List<Order> orders;

    private Date registered_at;

}
