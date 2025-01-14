package io.reactivestax.spring_boot_app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.reactivestax.spring_boot_app.dto.EmployeeDTO;
import io.reactivestax.spring_boot_app.dto.EmployeeFullDTO;
import io.reactivestax.spring_boot_app.exception.ResourceNotFoundException;
import io.reactivestax.spring_boot_app.service.EmployeeService;
import io.reactivestax.spring_boot_app.validation.CreateGroup;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/employees")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        Optional<EmployeeDTO> employee = employeeService.findById(id);
        return employee.map(ResponseEntity::ok)
        .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));
    }

    @PostMapping("/employee")
    public EmployeeDTO createEmployee2(@Validated(CreateGroup.class) @RequestBody EmployeeDTO employeeDTO) {
        // throw new RuntimeException("some error");
        EmployeeDTO savedEmployee = employeeService.save(employeeDTO);
        log.debug("Employee saved: " + savedEmployee);
        return savedEmployee;
    }
    
    @PostMapping("/employeenew")
    public String createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        // Create logic
        return "Employee created";
    }

    @PostMapping("/employeeexisting")
    public String updateEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        // Update logic
        return "Employee updated";
    }

    @PostMapping("/fullemployee")
    public ResponseEntity<EmployeeFullDTO> createEmployee(@Valid @RequestBody EmployeeFullDTO employeeFullDTO) {
        return ResponseEntity.ok().body(employeeFullDTO);
    }

    @PutMapping("/employee")
    public ResponseEntity<EmployeeDTO> putEmployee(@Validated(CreateGroup.class) @RequestBody EmployeeDTO employeeDetails) {
        Optional<EmployeeDTO> employee = employeeService.findById(employeeDetails.getId());
        if (employee.isPresent()) {
            EmployeeDTO updatedEmployee = employee.get();
            updatedEmployee.setFirstName(employeeDetails.getFirstName());
            updatedEmployee.setLastName(employeeDetails.getLastName());
            updatedEmployee.setEmail(employeeDetails.getEmail());
            updatedEmployee.setAddressId(employeeDetails.getAddressId());
            updatedEmployee.setDepartmentId(employeeDetails.getDepartmentId());
            updatedEmployee.setWorkGroupIds(employeeDetails.getWorkGroupIds());
            return ResponseEntity.ok(employeeService.save(updatedEmployee));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        if (employeeService.findById(id).isPresent()) {
            employeeService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{employeeId}/address/{addressId}")
    public ResponseEntity<String> associateEmployeeWithAddress(
            @PathVariable Long employeeId,
            @PathVariable Long addressId) {
        employeeService.associateEmployeeWithAddress(employeeId, addressId);
        return ResponseEntity.ok("Address associated with Employee");
    }

    @PostMapping("/{employeeId}/department/{departmentId}")
    public ResponseEntity<String> addEmployeeToDepartment(
            @PathVariable Long employeeId,
            @PathVariable Long departmentId) {
        employeeService.addEmployeeToDepartment(employeeId, departmentId);
        return ResponseEntity.ok("Employee added to Department");
    }

    @PostMapping("/{employeeId}/workgroups")
    public ResponseEntity<String> addEmployeeToWorkGroups(
            @PathVariable Long employeeId,
            @RequestBody List<Long> workGroupIds) {
        employeeService.addEmployeeToWorkGroups(employeeId, workGroupIds);
        return ResponseEntity.ok("Employee added to WorkGroups");
    }
}