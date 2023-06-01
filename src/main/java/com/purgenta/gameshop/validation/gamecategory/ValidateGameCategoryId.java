package com.purgenta.gameshop.validation.gamecategory;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CategoryIdValidator.class)
public @interface ValidateGameCategoryId {
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String message() default "Such a category doesn't exist";
}
