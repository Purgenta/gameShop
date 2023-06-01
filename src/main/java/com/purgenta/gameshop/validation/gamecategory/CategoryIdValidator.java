package com.purgenta.gameshop.validation.gamecategory;

import com.purgenta.gameshop.services.game.IGameCategoryService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryIdValidator implements ConstraintValidator<ValidateGameCategoryId, Integer> {
    @Autowired
    private IGameCategoryService iGameCategoryService;
    @Override
    public void initialize(ValidateGameCategoryId constraintAnnotation) {
        org.springframework.web.context.support.SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        var category = iGameCategoryService.getCategory(integer);
        return category.isPresent();
    }
}
