package com.purgenta.gameshop.validation.user;

import com.purgenta.gameshop.validation.user.ValidatePassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidatePassword, String> {
    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        if (password == null) return false;
        return password.matches("^[A-Za-z0-9_.]{5,16}$");
    }
}
