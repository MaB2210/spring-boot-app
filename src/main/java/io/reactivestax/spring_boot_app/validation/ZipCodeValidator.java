package io.reactivestax.spring_boot_app.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ZipCodeValidator implements ConstraintValidator<ValidZipCode, String> {

    @Override
    public boolean isValid(String zipCodeString, ConstraintValidatorContext context) {
        // A valid zip code must be exactly 5 digits
        // return zipCodeString != null && zipCodeString.matches("\\d{5}");


        if (!zipCodeString.matches("\\d{5}")) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate("zipCode should be valid 5 digit zip code")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
