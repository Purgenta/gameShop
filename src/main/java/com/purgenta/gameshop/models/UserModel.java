package com.purgenta.gameshop.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "user")
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long user_id;
    @Email
    @Column(name = "email")
    private String email;
    @Pattern(regexp = "^[A-Za-z0-9_.]{5,16}$", message = "Password must be between 5 and 16 characters long")
    @Column(name = "password")
    private String password;
    private String gender = "male";
}
