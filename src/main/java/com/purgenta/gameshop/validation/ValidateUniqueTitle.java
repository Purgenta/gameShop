package com.purgenta.gameshop.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = GameTitleUniqueValidator.class)
public @interface ValidateUniqueTitle {
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String message() default "A game under that name already exists";
}
