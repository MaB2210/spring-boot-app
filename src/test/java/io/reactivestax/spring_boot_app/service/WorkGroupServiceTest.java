package io.reactivestax.spring_boot_app.service;

import io.reactivestax.spring_boot_app.domain.Address;
import io.reactivestax.spring_boot_app.domain.WorkGroup;
import io.reactivestax.spring_boot_app.dto.AddressDTO;
import io.reactivestax.spring_boot_app.dto.WorkGroupDTO;
import io.reactivestax.spring_boot_app.repository.WorkGroupRepository;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;

@SpringBootTest
@ActiveProfiles("test")
class WorkGroupServiceTest {

    @Autowired
    WorkGroupService workGroupService;

    @MockitoBean
    WorkGroupRepository mockWorkGroupRepository;

    private static WorkGroup workGroup;

    @BeforeAll
    static void beforeAll() {
        workGroup = new WorkGroup();
        workGroup.setId(10L);
        workGroup.setName("abc");
    }

    @Test
    public void testfindAll() {
        Mockito.when(mockWorkGroupRepository.findAll()).thenReturn(
                Arrays.asList(workGroup));

        assertThat(workGroupService.findAll()).hasSize(1);
    }

    @Test
    public void testFindById() {
        Mockito.when(mockWorkGroupRepository.findById(10L)).thenReturn(Optional.of(workGroup));

        Optional<WorkGroupDTO> workGroupDTO = workGroupService.findById(10L);

        assertThat(workGroupDTO.isPresent());
        assertThat(workGroupDTO.get().getName()).isEqualTo(workGroup.getName());
    }

    @Test
    public void testSave() {
        Mockito.when(mockWorkGroupRepository.save(any(WorkGroup.class))).thenReturn(workGroup);

        WorkGroupDTO workGroupDTO = new WorkGroupDTO();
        workGroupDTO.setId(10L);
        workGroupDTO.setName("abc");

        WorkGroupDTO savedWorkGroup = workGroupService.save(workGroupDTO);
        assertThat(savedWorkGroup.getName()).isEqualTo(workGroupDTO.getName());
    }

    @Test
    public void testDeleteById(){
        Mockito.doNothing().when(mockWorkGroupRepository).deleteById(anyLong());
        workGroupService.deleteById(10L);
        Mockito.verify(mockWorkGroupRepository,times(1)).deleteById(10L);
    }
}