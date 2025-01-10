package io.reactivestax.spring_boot_app.validation;

import io.reactivestax.spring_boot_app.dto.EventDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, EventDTO> {

    @Override
    public boolean isValid(EventDTO eventDTO, ConstraintValidatorContext context) {
        if (eventDTO.getStartDate() == null || eventDTO.getEndDate() == null) {
            return true; // Skip validation if dates are null; use @NotNull for these fields
        }

        boolean isValid = eventDTO.getStartDate().isBefore(eventDTO.getEndDate());
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("End date must be after start date")
                    .addConstraintViolation();
        }
        return isValid;
    }
}
