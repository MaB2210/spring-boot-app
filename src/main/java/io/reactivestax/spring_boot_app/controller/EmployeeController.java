package io.reactivestax.spring_boot_app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @GetMapping
    public List<EmployeeDTO> getAllEmployees() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        Optional<EmployeeDTO> employee = service.findById(id);
        return employee.map(ResponseEntity::ok)
        .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));
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

    @PostMapping("/employee")
    public EmployeeDTO createEmployee2(@Valid @RequestBody EmployeeDTO employeeDTO) {
        //throw new RuntimeException("some error");
        return service.save(employeeDTO);
    }

    @PostMapping("/fullemployee")
    public ResponseEntity<EmployeeFullDTO> createEmployee(@Valid @RequestBody EmployeeFullDTO employeeFullDTO) {
        return ResponseEntity.ok().body(employeeFullDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDetails) {
        Optional<EmployeeDTO> employee = service.findById(id);
        if (employee.isPresent()) {
            EmployeeDTO updatedEmployee = employee.get();
            updatedEmployee.setFirstName(employeeDetails.getFirstName());
            updatedEmployee.setLastName(employeeDetails.getLastName());
            updatedEmployee.setEmail(employeeDetails.getEmail());
            updatedEmployee.setAddressId(employeeDetails.getAddressId());
            updatedEmployee.setDepartmentId(employeeDetails.getDepartmentId());
            updatedEmployee.setWorkGroupIds(employeeDetails.getWorkGroupIds());
            return ResponseEntity.ok(service.save(updatedEmployee));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        if (service.findById(id).isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{employeeId}/address/{addressId}")
    public ResponseEntity<String> associateEmployeeWithAddress(
            @PathVariable Long employeeId,
            @PathVariable Long addressId) {
        service.associateEmployeeWithAddress(employeeId, addressId);
        return ResponseEntity.ok("Address associated with Employee");
    }

    @PostMapping("/{employeeId}/department/{departmentId}")
    public ResponseEntity<String> addEmployeeToDepartment(
            @PathVariable Long employeeId,
            @PathVariable Long departmentId) {
        service.addEmployeeToDepartment(employeeId, departmentId);
        return ResponseEntity.ok("Employee added to Department");
    }

    @PostMapping("/{employeeId}/workgroups")
    public ResponseEntity<String> addEmployeeToWorkGroups(
            @PathVariable Long employeeId,
            @RequestBody List<Long> workGroupIds) {
        service.addEmployeeToWorkGroups(employeeId, workGroupIds);
        return ResponseEntity.ok("Employee added to WorkGroups");
    }
}