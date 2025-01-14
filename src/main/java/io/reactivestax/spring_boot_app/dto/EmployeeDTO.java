package io.reactivestax.spring_boot_app.dto;

import java.util.List;

import io.reactivestax.spring_boot_app.validation.CreateGroup;
import io.reactivestax.spring_boot_app.validation.UpdateGroup;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    @NotNull(groups = UpdateGroup.class, message = "ID must not be null for update operations")
    private Long id;

    @NotBlank(groups = {CreateGroup.class, UpdateGroup.class}, message = "First name must not be blank")
    //@NotBlank(message = "First name must not be blank")
    @Size(max = 50, message = "First name must not exceed 50 characters")
    private String firstName;

    @NotBlank(groups = {CreateGroup.class, UpdateGroup.class}, message = "Last name must not be blank")
    // @NotBlank(message = "Last name must not be blank")
    @Size(max = 50, message = "Last name must not exceed 50 characters")
    private String lastName;


    @NotBlank(groups = {CreateGroup.class, UpdateGroup.class}, message = "Email must not be blank")
    // @NotBlank(message = "Email must not be blank")
    @Email(message = "Email must be a valid email address")
    private String email;

    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 65, message = "Age must not exceed 65")
    private int age;

    private Long addressId;

    private Long departmentId;

    @Size(min = 1, message = "Employee must belong to at least one work group")
    @NotNull
    private List<Long> workGroupIds;
}
