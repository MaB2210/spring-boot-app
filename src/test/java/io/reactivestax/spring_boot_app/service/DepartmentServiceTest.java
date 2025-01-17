package io.reactivestax.spring_boot_app.service;

import io.reactivestax.spring_boot_app.domain.Department;
import io.reactivestax.spring_boot_app.dto.DepartmentDTO;
import io.reactivestax.spring_boot_app.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;

@SpringBootTest
@ActiveProfiles("test")
class DepartmentServiceTest {

    @Autowired
    DepartmentService departmentService;

    @MockitoBean
    DepartmentRepository mockedDepartmentRepository;

    private static Department department;

    @BeforeAll
    static void beforeAll() {
        department = new Department();
        department.setId(10L);
        department.setName("abc");
    }

    @Test
    public void testfindAll(){
        Mockito.when(mockedDepartmentRepository.findAll()).thenReturn(Arrays.asList(department));
        assertThat(departmentService.findAll()).hasSize(1);
    }

    @Test
    public void testfindById(){
        Mockito.when(mockedDepartmentRepository.findById(10L)).thenReturn(Optional.of(department));

        Optional<DepartmentDTO> departmentDTO = departmentService.findById(10L);
        assertThat(departmentDTO).isNotNull();
        assertThat(departmentDTO.get().getName().equals(department.getName()));
    }

    @Test
    public void testSave(){
        Mockito.when(mockedDepartmentRepository.save(any(Department.class))).thenReturn(department);

        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setId(10L);
        departmentDTO.setName("abc");

        DepartmentDTO savedDepartment = departmentService.save(departmentDTO);
        assertThat(savedDepartment).isNotNull();
        assertThat(savedDepartment.getName().equals(departmentDTO.getName()));
    }

    @Test
    public void testDelete(){
        Mockito.doNothing().when(mockedDepartmentRepository).deleteById(anyLong());
        departmentService.deleteById(10L);
        Mockito.verify(mockedDepartmentRepository,times(1)).deleteById(10L);
    }
}