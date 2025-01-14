package io.reactivestax.spring_boot_app.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import io.reactivestax.spring_boot_app.domain.Employee;
import io.reactivestax.spring_boot_app.dto.EmployeeDTO;
import io.reactivestax.spring_boot_app.repository.EmployeeRepository;
import io.reactivestax.spring_boot_app.service.EmployeeService;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest // This annotation is used to load the Spring context before the test is run
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @MockitoBean
    private EmployeeRepository employeeRepository;

    @Test
    public void testFindAll() {
        Mockito.when(employeeRepository.findAll()).thenReturn(
                Arrays.asList(Employee.builder().firstName("John").lastName("Doe").build()));

        assertThat(employeeService.findAll()).hasSize(1);
    }

    @Test
    public void testFindById() {
        Mockito.when(employeeRepository.findById(1L)).thenReturn(
                Optional.of(Employee.builder().id(1L).firstName("John").lastName("Doe").build()));

        Optional<EmployeeDTO> employee = employeeService.findById(1L);
        assertThat(employee).isPresent();
        assertThat(employee.get().getFirstName()).isEqualTo("John");
    } 

    @Test
    public void testSave() {
        Employee employee = Employee.builder().id(1L).firstName("John").lastName("Doe").build();
        Mockito.when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        EmployeeDTO employeeDTO = EmployeeDTO.builder().firstName("John").lastName("Doe").build();
        EmployeeDTO savedEmployee = employeeService.save(employeeDTO);
        assertThat(savedEmployee.getFirstName()).isEqualTo("John");
    }
}
