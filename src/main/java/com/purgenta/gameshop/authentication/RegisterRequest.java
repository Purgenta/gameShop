package com.purgenta.gameshop.authentication;

import com.purgenta.gameshop.validation.ValidateUniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    @Email
    @NotNull
    @ValidateUniqueEmail
    private String email;

    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9_.]{5,16}$", message = "Password must be between 5 and 16 characters long")
    private String password;
}
