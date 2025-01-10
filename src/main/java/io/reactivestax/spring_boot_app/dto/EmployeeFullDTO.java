package io.reactivestax.spring_boot_app.dto;

import java.util.List;

import io.reactivestax.spring_boot_app.validation.ValidAddress;
import lombok.Data;

@ValidAddress
@Data
public class EmployeeFullDTO {
    //flat fields
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    //nested objects and collections
    private AddressDTO address;
    private List<WorkGroupDTO> workgroups;
    private DepartmentDTO department;
}
