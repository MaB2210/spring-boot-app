package io.reactivestax.spring_boot_app.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import io.reactivestax.spring_boot_app.dto.EmployeeDTO;
import io.reactivestax.spring_boot_app.service.EmployeeService;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeService employeeService;

    @Test
    public void whenGetRequestToEmployees_thenCorrectResponse() throws Exception {

        EmployeeDTO employee1 = new EmployeeDTO();
        employee1.setId(10L);
        employee1.setFirstName("John5");
        employee1.setLastName("Doe5");
        employee1.setEmail("john.doe5@example.com");
        employee1.setAge(0);
        employee1.setAddressId(null);
        employee1.setDepartmentId(null);
        employee1.setWorkGroupIds(Collections.emptyList());

        EmployeeDTO employee2 = new EmployeeDTO();
        employee2.setId(11L);
        employee2.setFirstName("John5");
        employee2.setLastName("Doe5");
        employee2.setEmail("john.doe5@example.com");
        employee2.setAge(0);
        employee2.setAddressId(null);
        employee2.setDepartmentId(null);
        employee2.setWorkGroupIds(Collections.emptyList());

        List<EmployeeDTO> employees = Arrays.asList(employee1,employee2);

        when(employeeService.findAll()).thenReturn(employees);

        String employeeJsonResponse = """
                        [{
                            "id": 10,
                            "firstName": "John5",
                            "lastName": "Doe5",
                            "email": "john.doe5@example.com",
                            "age": 0,
                            "addressId": null,
                            "departmentId": null,
                            "workGroupIds": []
                        },
                        {
                            "id": 11,
                            "firstName": "John5",
                            "lastName": "Doe5",
                            "email": "john.doe5@example.com",
                            "age": 0,
                            "addressId": null,
                            "departmentId": null,
                            "workGroupIds": []
                        }]
                """;

        mockMvc.perform(get("/api/employees")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(employeeJsonResponse));
    }

    @Test
    public void whenGetRequestToEmployeeById_thenCorrectResponse() throws Exception {
        EmployeeDTO employee1 = new EmployeeDTO();
        employee1.setId(10L);
        employee1.setFirstName("John5");
        employee1.setLastName("Doe5");
        employee1.setEmail("john.doe5@example.com");
        employee1.setAge(0);
        employee1.setAddressId(null);
        employee1.setDepartmentId(null);
        employee1.setWorkGroupIds(Collections.emptyList());

        when(employeeService.findById(10L)).thenReturn(Optional.of(employee1));

        String employeeJsonResponse = """
                        {
                            "id": 10,
                            "firstName": "John5",
                            "lastName": "Doe5",
                            "email": "john.doe5@example.com",
                            "age": 0,
                            "addressId": null,
                            "departmentId": null,
                            "workGroupIds": []
                        }
                """;

        mockMvc.perform(get("/api/employees/10")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(employeeJsonResponse));
    }

    @Test
    public void whenPostRequestToCreateEmployee_thenCorrectResponse() throws Exception {
        String employeeJson = """
                        {
                          "firstName": "John5",
                          "lastName": "Doe5",
                          "email": "john.doe5@gmail.com",
                          "age":64,
                          "workGroupIds": [1,2,3]
                        }
                """;

        mockMvc.perform(post("/api/employees/employee")
                .content(employeeJson)
                .contentType(MediaType.APPLICATION_JSON))
                // .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Employee created"));
    }


    @Test
    public void whenPostRequestToCreateEmployeeWithMissingFirstName_thenBadRequestResponse() throws Exception {
        String employeeJson = """
                        {
                          "lastName": "Doe5",
                          "email": "john.doe5@gmail.com",
                          "age":64,
                          "workGroupIds": [1,2,3]
                        }
                """;

        mockMvc.perform(post("/api/employees/employee")
                .content(employeeJson)
                .contentType(MediaType.APPLICATION_JSON))
                // .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Employee created"));
    }

    @Test
    public void whenPostRequestToCreateEmployeeAndInValidEmployee_thenValidationError() throws Exception {
        String employeeJson = "{\"firstName\": \"\"}";

        mockMvc.perform(post("/api/employees/employeenew")
                .content(employeeJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
