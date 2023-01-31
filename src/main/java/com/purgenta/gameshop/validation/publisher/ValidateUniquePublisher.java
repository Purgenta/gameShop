package com.purgenta.gameshop.validation.publisher;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PublisherUniqueValidator.class)
public @interface ValidateUniquePublisher {
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String message() default "A publisher with such name already exists";
}
