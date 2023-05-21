package com.purgenta.gameshop.validation.publisher;

import com.purgenta.gameshop.models.game.Publisher;
import com.purgenta.gameshop.services.publisher.IPublisherService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;


public class PublisherIdValidator implements ConstraintValidator<ValidatePublisherId, Integer> {
    @Autowired
    private IPublisherService publisherService;
    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        Optional<Publisher> publisher = publisherService.getPublisherById(integer);
        return publisher.isPresent();
    }
}
