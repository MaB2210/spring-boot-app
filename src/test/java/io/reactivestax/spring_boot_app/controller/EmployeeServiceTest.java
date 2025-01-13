package io.reactivestax.spring_boot_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import io.reactivestax.spring_boot_app.repository.EmployeeRepository;
import io.reactivestax.spring_boot_app.service.EmployeeService;

@SpringBootTest
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @MockitoBean
    private EmployeeRepository employeeRepository;

    // @Test
    // public void testFindAll() {
    //     Mockito.when(employeeRepository.findAll()).thenReturn(
    //             Arrays.asList(new Employee(1L, "John", "Doe", "john.doe@example.com")));

    //     assertThat(employeeService.findAll()).hasSize(1);
    // }

    // @Test
    // public void testFindById() {
    //     Mockito.when(employeeRepository.findById(1L)).thenReturn(
    //             Optional.of(new Employee(1L, "John", "Doe", "john.doe@example.com")));

    //     Optional<EmployeeDTO> employee = employeeService.findById(1L);
    //     assertThat(employee).isPresent();
    //     assertThat(employee.get().getFirstName()).isEqualTo("John");
    // }

    // @Test
    // public void testSave() {
    //     Employee employee = new Employee(1L, "John", "Doe", "john.doe@example.com");
    //     Mockito.when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

    //     EmployeeDTO employeeDTO = new EmployeeDTO(null, "John", "Doe", "john.doe@example.com", null, null, null);
    //     EmployeeDTO savedEmployee = employeeService.save(employeeDTO);
    //     assertThat(savedEmployee.getFirstName()).isEqualTo("John");
    // }
}
