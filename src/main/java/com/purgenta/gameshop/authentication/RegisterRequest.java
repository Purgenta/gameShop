package com.purgenta.gameshop.authentication;

import com.purgenta.gameshop.validation.user.ValidatePassword;
import com.purgenta.gameshop.validation.user.ValidateUniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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

    @NotNull
    @NotBlank
    @Email
    @ValidateUniqueEmail
    private String email;

    @NotNull
    @NotBlank
    @ValidatePassword
    private String password;
}
