package io.reactivestax.spring_boot_app.validation;

import io.reactivestax.spring_boot_app.dto.EmployeeFullDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AddressValidator implements ConstraintValidator<ValidAddress, EmployeeFullDTO> {

    @Override
    public boolean isValid(EmployeeFullDTO employee, ConstraintValidatorContext context) {
        if (employee.getAddress() == null) {
            return false; // Address must be provided
        }
        return employee.getAddress().getCity() != null && employee.getAddress().getState() != null;
    }
}
