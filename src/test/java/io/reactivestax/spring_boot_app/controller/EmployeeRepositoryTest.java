package io.reactivestax.spring_boot_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import io.reactivestax.spring_boot_app.repository.EmployeeRepository;

@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    // @Test
    // public void testFindByDepartment() {
    //     Employee employee1 = new Employee();
    //     employee1.setFirstName("John");
    //     employee1.setLastName("Doe");
    //     employee1.setDepartment("HR");

    //     Employee employee2 = new Employee();
    //     employee2.setFirstName("Jane");
    //     employee2.setLastName("Smith");
    //     employee2.setDepartment("HR");

    //     employeeRepository.save(employee1);
    //     employeeRepository.save(employee2);

    //     List<Employee> employees = employeeRepository.findByDepartment("HR", Sort.by("firstName"));
    //     assertThat(employees).hasSize(2);
    //     assertThat(employees.get(0).getFirstName()).isEqualTo("Jane");
    // }

    // @Test
    // public void testFindByDepartmentId() {
    //     Employee employee = new Employee();
    //     employee.setFirstName("John");
    //     employee.setLastName("Doe");
    //     employee.setDepartmentId(1L);

    //     employeeRepository.save(employee);

    //     List<Employee> employees = employeeRepository.findByDepartmentId(1L, Sort.by("lastName"));
    //     assertThat(employees).hasSize(1);
    //     assertThat(employees.get(0).getLastName()).isEqualTo("Doe");
    // }
}
