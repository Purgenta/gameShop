package com.purgenta.gameshop.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ImageValidator.class})
public @interface ValidateImage {
    String message() default "File has to be an image file";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
