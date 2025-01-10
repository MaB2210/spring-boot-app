package io.reactivestax.spring_boot_app.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = DateRangeValidator.class)
@Target(ElementType.TYPE) // Apply at the class level
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDateRange {
    String message() default "End date must be after start date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
