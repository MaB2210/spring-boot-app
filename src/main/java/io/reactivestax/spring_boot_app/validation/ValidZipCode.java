package io.reactivestax.spring_boot_app.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = ZipCodeValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidZipCode {
    String message() default "Invalid zip code. Must be 5 digits.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
