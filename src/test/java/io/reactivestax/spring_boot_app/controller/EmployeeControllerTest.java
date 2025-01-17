package io.reactivestax.spring_boot_app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivestax.spring_boot_app.domain.Address;
import io.reactivestax.spring_boot_app.domain.Department;
import io.reactivestax.spring_boot_app.domain.Employee;
import io.reactivestax.spring_boot_app.domain.WorkGroup;
import io.reactivestax.spring_boot_app.dto.EmployeeDTO;
import io.reactivestax.spring_boot_app.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

        List<EmployeeDTO> employees = Arrays.asList(employee1, employee2);

        when(employeeService.findAll()).thenReturn(employees);

//        String employeeJsonResponse = """
//                        [{
//                            "id": 10,
//                            "firstName": "John5",
//                            "lastName": "Doe5",
//                            "email": "john.doe5@example.com",
//                            "age": 0,
//                            "addressId": null,
//                            "departmentId": null,
//                            "workGroupIds": []
//                        },
//                        {
//                            "id": 11,
//                            "firstName": "John5",
//                            "lastName": "Doe5",
//                            "email": "john.doe5@example.com",
//                            "age": 0,
//                            "addressId": null,
//                            "departmentId": null,
//                            "workGroupIds": []
//                        }]
//                """;

        ObjectMapper objectMapper = new ObjectMapper();
        String employeeJsonResponse = objectMapper.writeValueAsString(employees);
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
        employee1.setAge(10);
        employee1.setAddressId(15L);
        employee1.setDepartmentId(10L);
        employee1.setWorkGroupIds(Arrays.asList(1L, 2L, 3L));

        when(employeeService.findById(10L)).thenReturn(Optional.of(employee1));

//        String employeeJsonResponse = """
//                        {
//                            "id": 10,
//                            "firstName": "John5",
//                            "lastName": "Doe5",
//                            "email": "john.doe5@example.com",
//                            "age": 10,
//                            "addressId": 15,
//                            "departmentId": 10,
//                            "workGroupIds": [1,2,3]
//                        }
//                """;

        ObjectMapper objectMapper = new ObjectMapper();
        String employeeJsonResponse = objectMapper.writeValueAsString(employee1);
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
                    "email": "john.doe5@example.com",
                    "age": 19,
                    "addressId": 15,
                    "departmentId": 10,
                    "workGroupIds": [1,2,3]
                }
                """;

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(35L);
        employeeDTO.setFirstName("John5");
        employeeDTO.setLastName("Doe5");
        employeeDTO.setEmail("john.doe5@example.com");
        employeeDTO.setAge(19);
        employeeDTO.setAddressId(15L);
        employeeDTO.setDepartmentId(10L);
        employeeDTO.setWorkGroupIds(Arrays.asList(1L, 2L, 3L));

        when(employeeService.save(any(EmployeeDTO.class))).thenReturn(employeeDTO);

        mockMvc.perform(post("/api/employees/employee")
                        .content(employeeJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) // This will print the request and response details
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(employeeJson));
    }

    @Test
    public void whenPostRequestToCreateEmployeeWithMissingFirstName_thenBadRequestResponse() throws Exception {
        String employeeJsonRequest = """
                {
                    "lastName": "Doe5",
                    "email": "john.doe5@example.com",
                    "age": 19,
                    "addressId": 15,
                    "departmentId": 10,
                    "workGroupIds": [1,2,3]
                }
                """;

        String missingFirstNameJsonResponse = """
                {
                    "firstName": "First name must not be blank"
                }
                """;
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(35L);
        employeeDTO.setFirstName("John5");
        employeeDTO.setLastName("Doe5");
        employeeDTO.setEmail("john.doe5@example.com");
        employeeDTO.setAge(19);
        employeeDTO.setAddressId(15L);
        employeeDTO.setDepartmentId(10L);
        employeeDTO.setWorkGroupIds(Arrays.asList(1L, 2L, 3L));

        when(employeeService.save(any(EmployeeDTO.class))).thenReturn(employeeDTO);

        mockMvc.perform(post("/api/employees/employee")
                        .content(employeeJsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) // This will print the request and response details
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(missingFirstNameJsonResponse))
        ;
    }

    //TASK: We can add more validation tests for all fields here 

    @Test
    public void whenPutRequestToCreateEmployee_thenOKResponse() throws Exception {
        String employeeJson = """
                {
                    "id" : 35,
                    "firstName": "John5",
                    "lastName": "Doe5",
                    "email": "john.doe5@example.com",
                    "age": 19,
                    "addressId": 15,
                    "departmentId": 10,
                    "workGroupIds": [1,2,3]
                }
                """;

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(35L);
        employeeDTO.setFirstName("John5");
        employeeDTO.setLastName("Doe5");
        employeeDTO.setEmail("john.doe5@example.com");
        employeeDTO.setAge(19);
        employeeDTO.setAddressId(15L);
        employeeDTO.setDepartmentId(10L);
        employeeDTO.setWorkGroupIds(Arrays.asList(1L, 2L, 3L));

        when(employeeService.findById(anyLong())).thenReturn(Optional.of(employeeDTO));
        when(employeeService.save(any(EmployeeDTO.class))).thenReturn(employeeDTO);

        mockMvc.perform(put("/api/employees/employee")
                        .content(employeeJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) // This will print the request and response details
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(employeeJson));
    }

    @Test
    public void whenPutRequestToCreateEmployeeThatDoesNotExist_thenNotFoundResponse() throws Exception {
        String employeeJson = """
                {
                    "id" : 35,
                    "firstName": "John5",
                    "lastName": "Doe5",
                    "email": "john.doe5@example.com",
                    "age": 19,
                    "addressId": 11,
                    "departmentId": 10,
                    "workGroupIds": [1,2,3]
                }
                """;

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(35L); //json above is missing the ID
        employeeDTO.setFirstName("John5");
        employeeDTO.setLastName("Doe5");
        employeeDTO.setEmail("john.doe5@example.com");
        employeeDTO.setAge(19);
        employeeDTO.setAddressId(15L);  //addressId is different compared to json above
        employeeDTO.setDepartmentId(10L);
        employeeDTO.setWorkGroupIds(Arrays.asList(1L, 2L, 3L));

        when(employeeService.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/employees/employee")
                        .content(employeeJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) // This will print the request and response details
                .andExpect(status().isNotFound())
                //.andExpect(content().contentType(MediaType.APPLICATION_JSON)) //it won't be set in this case
                .andExpect(content().string(""));
    }


    @Test
    public void whenDeleteRequestToEmployeeById_thenCorrectResponse() throws Exception {
        EmployeeDTO employee1 = new EmployeeDTO();

        when(employeeService.findById(10L)).thenReturn(Optional.of(employee1));

        // Perform the FIRST DELETE request to verify the case when employee is FOUND
        mockMvc.perform(delete("/api/employees/10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) // This will print the request and response details
                .andExpect(status().isNoContent())
                .andExpect(content().string("")); // Expecting no content in the response body

        when(employeeService.findById(10L)).thenReturn(Optional.empty());
        // Perform the SECOND DELETE request to verify the case when employee is not found
        mockMvc.perform(delete("/api/employees/10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) // This will print the request and response details
                .andExpect(status().isNotFound())
                .andExpect(content().string("")); // Expecting no content in the response body

    }


    @Test
    public void whenPostRequestToAssociateEmployeeWithAddress_thenCorrectResponse() throws Exception {
        Employee employee = new Employee();
        employee.setId(10L);
        employee.setFirstName("John5");
        employee.setLastName("Doe5");
        employee.setEmail("john.doe5@example.com");

        Address address = new Address();
        address.setId(20L);

        doNothing().when(employeeService).associateEmployeeWithAddress(10L,20L);

        mockMvc.perform(post("/api/employees/10/address/20"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Address associated with Employee"));

    }

    @Test
    public void whenPostRequestToAssociateEmployeeWithDepartment_thenCorrectResponse() throws Exception {
        Employee employee = new Employee();
        employee.setId(10L);
        employee.setFirstName("John5");
        employee.setLastName("Doe5");
        employee.setEmail("john.doe5@example.com");

        Department department = new Department();
        department.setId(20L);

        doNothing().when(employeeService).addEmployeeToDepartment(10L,20L);

        mockMvc.perform(post("/api/employees/10/department/20"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Employee added to Department"));

    }

    @Test
    public void whenPostRequestToAssociateEmployeeWithWorkGroup_thenCorrectResponse() throws Exception {
        Employee employee = new Employee();
        employee.setId(10L);
        employee.setFirstName("John5");
        employee.setLastName("Doe5");
        employee.setEmail("john.doe5@example.com");

        WorkGroup workGroup1 = new WorkGroup();
        workGroup1.setId(20L);
        WorkGroup workGroup2 = new WorkGroup();
        workGroup2.setId(30L);
        WorkGroup workGroup3 = new WorkGroup();
        workGroup3.setId(40L);
        List<Long> workGroupIds = Arrays.asList(20L,30L,40L);

        doNothing().when(employeeService).addEmployeeToWorkGroups(10L,workGroupIds);

        String workGroupJson = "[20,30,40]";

         mockMvc.perform(post("/api/employees/10/workgroups")
                .content(workGroupJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Employee added to WorkGroups"));
    }
}
