package io.reactivestax.spring_boot_app.dto;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmployeeDTO {
    private Long id;
    @NotBlank(message = "First name must not be blank")
    @Size(max = 50, message = "First name must not exceed 50 characters")
    private String firstName;

    @NotBlank(message = "Last name must not be blank")
    @Size(max = 50, message = "Last name must not exceed 50 characters")
    private String lastName;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email must be a valid email address")
    private String email;

    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 65, message = "Age must not exceed 65")
    private int age;

    private Long addressId;

    private Long departmentId;
    private List<Long> workGroupIds;
}

