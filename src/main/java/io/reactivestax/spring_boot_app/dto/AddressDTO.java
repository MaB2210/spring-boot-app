package io.reactivestax.spring_boot_app.dto;

import io.reactivestax.spring_boot_app.validation.ValidZipCode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddressDTO {
    private Long id;

    @NotBlank(message = "Street must not be blank")
    @Size(max = 100, message = "Street must not exceed 100 characters")
    private String street;

    @NotBlank(message = "City must not be blank")
    @Size(max = 50, message = "City must not exceed 50 characters")
    private String city;

    @NotBlank(message = "State must not be blank")
    @Size(max = 50, message = "State must not exceed 50 characters")
    private String state;

    @NotBlank(message = "Zip Code must not be blank")
    @Pattern(regexp = "\\d{5}", message = "Zip Code must be a 5-digit number")
    private String zipCode;

    @NotBlank(message = "zipCodeTwo has to be non blank")
    @ValidZipCode
    private String zipCodeTwo;
}
