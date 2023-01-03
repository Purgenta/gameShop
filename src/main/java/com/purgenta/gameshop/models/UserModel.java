package com.purgenta.gameshop.models;

import com.purgenta.gameshop.validation.ValidateUniqueEmail;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity(name = "user")
@Table(name = "user")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long user_id;
    @NotNull(message = "Email can't be null")
    @Email
    @ValidateUniqueEmail
    @Column(name = "email")
    private String email;
    @Pattern(regexp = "^[A-Za-z0-9_.]{5,16}$", message = "Password must be between 5 and 16 characters long")
    @Column(name = "password")
    @NotNull(message = "Password can't be null")
    private String password;
    private String gender = "male";
}
