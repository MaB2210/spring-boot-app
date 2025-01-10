package io.reactivestax.spring_boot_app.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ZipCodeValidator implements ConstraintValidator<ValidZipCode, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // A valid zip code must be exactly 5 digits
        return value != null && value.matches("\\d{5}");
    }
}
