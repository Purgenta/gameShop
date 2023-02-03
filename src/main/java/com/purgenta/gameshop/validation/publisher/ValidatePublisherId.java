package com.purgenta.gameshop.validation.publisher;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PublisherIdValidator.class)
public @interface ValidatePublisherId {
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String message() default "Such a publisher doesn't exist";
}
