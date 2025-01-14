package io.reactivestax.spring_boot_app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import io.reactivestax.spring_boot_app.domain.Address;
import io.reactivestax.spring_boot_app.domain.Department;
import io.reactivestax.spring_boot_app.domain.Employee;
import io.reactivestax.spring_boot_app.domain.WorkGroup;

//This works with Embedded Database
// H2 or HSQL or Derby (which ever you put in the classpath test scope)
@DataJpaTest
public class EmployeeRepositoryWithEmbeddedDBTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    //Use the following hooks to insert/delete any common data
    //@BeforeEach
    //@BeforeAll

    @Test
    public void testSaveAndRetrieveEmployeeWithAddress() {
        //setup the test specific
        // data in the test itself using @autowired jdbcTemplate

        // Arrange: Create Address and Employee
        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("New York");
        // address.setPostalCode("10001");

        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .address(address) // Associate address with employee
                .build();

        // Act: Save and retrieve employee
        Employee savedEmployee = employeeRepository.save(employee);
        Employee foundEmployee = employeeRepository.findById(savedEmployee.getId()).orElse(null);

        // Assert
        assertThat(foundEmployee).isNotNull();
        assertThat(foundEmployee.getAddress()).isNotNull();
        assertThat(foundEmployee.getAddress().getStreet()).isEqualTo("123 Main St");
        //add more detailed assertions here. the more the better.
    }

    @Test
    public void testSaveAndRetrieveEmployeeWithDepartment() {
        // Arrange: Create Department and Employee
        Department department = new Department();
        department.setName("IT");

        Employee employee = Employee.builder()
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@example.com")
                .department(department) // Associate department with employee
                .build();

        // Act: Save and retrieve employee
        Employee savedEmployee = employeeRepository.save(employee);
        Employee foundEmployee = employeeRepository.findById(savedEmployee.getId()).orElse(null);

        // Assert
        assertThat(foundEmployee).isNotNull();
        assertThat(foundEmployee.getDepartment()).isNotNull();
        assertThat(foundEmployee.getDepartment().getName()).isEqualTo("IT");
        //add more detailed assertions here. the more the better.
    }

    @Test
    public void testSaveAndRetrieveEmployeeWithWorkGroups() {
        // Arrange: Create WorkGroups and Employee
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
                .workGroups(workGroups) // Associate work groups with employee
                .build();

        group1.setEmployees(List.of(employee)); // Associate employee with group1
        group2.setEmployees(List.of(employee)); // Associate employee with group2

        // Act: Save and retrieve employee
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
        // Arrange: Create Employee with Address
        Address address = new Address();
        address.setStreet("456 Elm St");
        address.setCity("San Francisco");
        // address.setPostalCode("94101");

        Employee employee = Employee.builder()
                .firstName("Bob")
                .lastName("Williams")
                .email("bob.williams@example.com")
                .address(address) // Associate address with employee
                .build();

        // Act: Save employee
        Employee savedEmployee = employeeRepository.save(employee);

        // Assert: Verify address is saved due to cascading
        assertThat(savedEmployee.getAddress()).isNotNull();
        assertThat(savedEmployee.getAddress().getStreet()).isEqualTo("456 Elm St");
    }

    @Test
    public void testDeleteEmployeeWithAddress() {
        // Arrange: Create Employee with Address
        Address address = new Address();
        address.setStreet("789 Pine St");
        address.setCity("Los Angeles");
        // address.setPostalCode("90001");

        Employee employee = Employee.builder()
                .firstName("Charlie")
                .lastName("Brown")
                .email("charlie.brown@example.com")
                .address(address) // Associate address with employee
                .build();

        Employee savedEmployee = employeeRepository.save(employee);

        // Act: Delete employee
        employeeRepository.deleteById(savedEmployee.getId());

        // Assert: Verify cascading delete of address
        assertThat(employeeRepository.findById(savedEmployee.getId())).isEmpty();
    }
}
