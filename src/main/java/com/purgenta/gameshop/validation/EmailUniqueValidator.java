package com.purgenta.gameshop.validation;

import com.purgenta.gameshop.models.User;
import com.purgenta.gameshop.repositories.IUserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailUniqueValidator implements ConstraintValidator<ValidateUniqueEmail, String> {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public void initialize(ValidateUniqueEmail constraintAnnotation) {
        org.springframework.web.context.support.SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        User user = userRepository.findByEmail(email);
        return user == null;
    }
}
