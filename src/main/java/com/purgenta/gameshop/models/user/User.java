package com.purgenta.gameshop.models.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.purgenta.gameshop.models.cart.Cart;
import com.purgenta.gameshop.models.game.Game;
import com.purgenta.gameshop.models.order.Order;
import com.purgenta.gameshop.validation.user.ValidatePassword;
import com.purgenta.gameshop.validation.user.ValidateUniqueEmail;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

@Data
@Entity(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long user_id;

    @NotNull
    @ValidateUniqueEmail
    @Column(unique = true, nullable = false)
    private String email;

    @NotNull
    @ValidatePassword
    @JsonIgnore
    @Column(nullable = false)
    private String password;
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user")
    private List<Cart> carts;
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user")
    private List<Order> orders;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Token> token;
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Game> user_games;

    @Column(nullable = false)
    @CreationTimestamp
    private Date registered_at;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
