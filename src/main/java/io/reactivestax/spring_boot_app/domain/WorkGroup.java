package io.reactivestax.spring_boot_app.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Entity
@Data
public class WorkGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "employee_workgroup", joinColumns = @JoinColumn(name = "workgroup_id"), inverseJoinColumns = @JoinColumn(name = "employee_id"))
    @JsonBackReference
    private List<Employee> employees = new ArrayList<>();

    

}