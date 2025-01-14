package io.reactivestax.spring_boot_app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import io.reactivestax.spring_boot_app.domain.Address;
import io.reactivestax.spring_boot_app.domain.Department;
import io.reactivestax.spring_boot_app.domain.Employee;
import io.reactivestax.spring_boot_app.domain.WorkGroup;

@DataJpaTest
@ActiveProfiles("test") // Ensure the application-test.properties is used
@Transactional // Ensures each test runs in its own transaction and rolls back after execution
public class EmployeeRepositoryWithRealDBTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testSaveAndRetrieveEmployeeWithAddress() {
        // Arrange
        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("New York");

        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .address(address)
                .build();

        // Act
        Employee savedEmployee = employeeRepository.save(employee);
        Employee foundEmployee = employeeRepository.findById(savedEmployee.getId()).orElse(null);

        // Assert
        assertThat(foundEmployee).isNotNull();
        assertThat(foundEmployee.getAddress()).isNotNull();
        assertThat(foundEmployee.getAddress().getStreet()).isEqualTo("123 Main St");
    }

    @Test
    public void testSaveAndRetrieveEmployeeWithDepartment() {
        // Arrange
        Department department = new Department();
        department.setName("IT");

        Employee employee = Employee.builder()
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@example.com")
                .department(department)
                .build();

        // Act
        Employee savedEmployee = employeeRepository.save(employee);
        Employee foundEmployee = employeeRepository.findById(savedEmployee.getId()).orElse(null);

        // Assert
        assertThat(foundEmployee).isNotNull();
        assertThat(foundEmployee.getDepartment()).isNotNull();
        assertThat(foundEmployee.getDepartment().getName()).isEqualTo("IT");
    }

    @Test
    public void testSaveAndRetrieveEmployeeWithWorkGroups() {
        // Arrange
        WorkGroup group1 = new WorkGroup();
        group1.setName("Team A");

        WorkGroup group2 = new WorkGroup();
        group2.setName("Team B");

        List<WorkGroup> workGroups = new ArrayList<>();
        workGroups.add(group1);
        workGroups.add(group2);

        Employee employee = Employee.builder()
                .firstName("Alice")
                .lastName("Johnson")
                .email("alice.johnson@example.com")
                .workGroups(workGroups)
                .build();

        group1.setEmployees(List.of(employee));
        group2.setEmployees(List.of(employee));

        // Act
        Employee savedEmployee = employeeRepository.save(employee);
        Employee foundEmployee = employeeRepository.findById(savedEmployee.getId()).orElse(null);

        // Assert
        assertThat(foundEmployee).isNotNull();
        assertThat(foundEmployee.getWorkGroups()).hasSize(2);
        assertThat(foundEmployee.getWorkGroups().get(0).getName()).isEqualTo("Team A");
        assertThat(foundEmployee.getWorkGroups().get(1).getName()).isEqualTo("Team B");
    }

    @Test
    public void testCascadingSaveForAddress() {
        // Arrange
        Address address = new Address();
        address.setStreet("456 Elm St");
        address.setCity("San Francisco");

        Employee employee = Employee.builder()
                .firstName("Bob")
                .lastName("Williams")
                .email("bob.williams@example.com")
                .address(address)
                .build();

        // Act
        Employee savedEmployee = employeeRepository.save(employee);

        // Assert
        assertThat(savedEmployee.getAddress()).isNotNull();
        assertThat(savedEmployee.getAddress().getStreet()).isEqualTo("456 Elm St");
    }

    @Test
    public void testDeleteEmployeeWithAddress() {
        // Arrange
        Address address = new Address();
        address.setStreet("789 Pine St");
        address.setCity("Los Angeles");

        Employee employee = Employee.builder()
                .firstName("Charlie")
                .lastName("Brown")
                .email("charlie.brown@example.com")
                .address(address)
                .build();

        Employee savedEmployee = employeeRepository.save(employee);

        // Act
        employeeRepository.deleteById(savedEmployee.getId());

        // Assert
        assertThat(employeeRepository.findById(savedEmployee.getId())).isEmpty();
    }
}
