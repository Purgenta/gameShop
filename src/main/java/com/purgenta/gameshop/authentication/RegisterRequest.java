package com.purgenta.gameshop.authentication;

import com.purgenta.gameshop.validation.ValidatePassword;
import com.purgenta.gameshop.validation.ValidateUniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
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
    @ValidatePassword
    private String password;
}
