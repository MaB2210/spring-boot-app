package io.reactivestax.spring_boot_app.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = AddressValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAddress {
    String message()

    default "Address is invalid";Class<?>[] groups() default {};

    Class<? extends Payload>[] payload()default{};
}
