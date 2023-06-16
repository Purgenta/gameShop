package com.purgenta.gameshop.validation.publisher;

import com.purgenta.gameshop.models.game.Publisher;
import com.purgenta.gameshop.repositories.IPublisherRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PublisherUniqueValidator implements ConstraintValidator<ValidateUniquePublisher,String> {
    @Autowired
   private IPublisherRepository publisherRepository;
    @Override
    public boolean isValid(String publisherName, ConstraintValidatorContext constraintValidatorContext) {
       Optional<Publisher> publisher = publisherRepository.findPublisherByName(publisherName);
       return publisher.isEmpty();
    }
}
