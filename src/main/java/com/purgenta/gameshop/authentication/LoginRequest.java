package com.purgenta.gameshop.authentication;

import com.purgenta.gameshop.validation.ValidatePassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @Email
    @NotNull
    private String email;
    @NotNull
    @ValidatePassword
    private String password;
}
