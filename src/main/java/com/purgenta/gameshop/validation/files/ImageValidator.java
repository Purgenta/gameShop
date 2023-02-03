package com.purgenta.gameshop.validation.files;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;


public class ImageValidator implements ConstraintValidator<ValidateImages, MultipartFile[]> {


    @Override
    public boolean isValid(MultipartFile[] multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        AtomicBoolean valid = new AtomicBoolean(true);
        Arrays.asList(multipartFile).forEach(file-> {
            String contentType = file.getContentType();
            assert contentType != null;
            valid.set(isSupportedContentType(contentType));
        });
        return valid.get();
    }
    private boolean isSupportedContentType(String contentType) {
        return contentType.equals("image/png")
                || contentType.equals("image/jpg")
                || contentType.equals("image/jpeg");
    }
}
