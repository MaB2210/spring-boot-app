package io.reactivestax.spring_boot_app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Validated // Enables validation at the method parameter level
@RestController
@RequestMapping("/api/employees_validated")
public class EmployeeValidatedController {

    @GetMapping("/{id}")
    public ResponseEntity<String> getEmployeeById(@PathVariable @Min(1) Long id) {
        // If `id` is less than 1, validation will fail
        return ResponseEntity.ok("Employee details for ID: " + id);
    }

    @GetMapping
    public ResponseEntity<String> getAllEmployees(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size) {
        // Validation ensures `page` is >= 0 and `size` is >= 1
        return ResponseEntity.ok("Fetching employees with pagination: page=" + page + ", size=" + size);
    }

    @PostMapping
    public ResponseEntity<String> createEmployee(/*@Validated does not work with RequestBody*/ @RequestBody @NotBlank String name) {
        // Now validates the name field
        return ResponseEntity.ok("Employee created: " + name);
    }

}