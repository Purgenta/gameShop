package com.purgenta.gameshop.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PasswordValidator.class)
public @interface ValidatePassword {
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String message() default "Password must be between 5 and 16 characters long";
}
