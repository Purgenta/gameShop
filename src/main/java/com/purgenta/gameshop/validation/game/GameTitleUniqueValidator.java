package com.purgenta.gameshop.validation.game;


import com.purgenta.gameshop.repositories.IGameRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class GameTitleUniqueValidator implements ConstraintValidator<ValidateUniqueTitle, String> {
    private final IGameRepository iGameRepository;

    public GameTitleUniqueValidator(IGameRepository iGameRepository) {
        this.iGameRepository = iGameRepository;
    }

    @Override
    public void initialize(ValidateUniqueTitle constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String title, ConstraintValidatorContext constraintValidatorContext) {
        return iGameRepository.findByTitle(title) == null;
    }
}
