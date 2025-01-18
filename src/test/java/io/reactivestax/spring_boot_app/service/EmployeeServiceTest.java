package io.reactivestax.spring_boot_app.service;

import io.reactivestax.spring_boot_app.domain.Address;
import io.reactivestax.spring_boot_app.domain.Department;
import io.reactivestax.spring_boot_app.domain.Employee;
import io.reactivestax.spring_boot_app.domain.WorkGroup;
import io.reactivestax.spring_boot_app.dto.EmployeeDTO;
import io.reactivestax.spring_boot_app.repository.AddressRepository;
import io.reactivestax.spring_boot_app.repository.DepartmentRepository;
import io.reactivestax.spring_boot_app.repository.EmployeeRepository;
import io.reactivestax.spring_boot_app.repository.WorkGroupRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;

@SpringBootTest // This annotation is used to load the Spring context before the test is run
@ActiveProfiles("test")
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @MockitoBean
    private EmployeeRepository mockEmployeeRepository;

    @MockitoBean
    private AddressRepository mockAddressRepository;

    @MockitoBean
    private DepartmentRepository mockDepartmentRepository;

    @MockitoBean
    private WorkGroupRepository mockWorkGroupRepository;


    @Test
    public void testFindAll() {
        Mockito.when(mockEmployeeRepository.findAll()).thenReturn(
                Arrays.asList(Employee.builder().firstName("John").lastName("Doe").build()));

        assertThat(employeeService.findAll()).hasSize(1);
    }

    @Test
    public void testFindById() {
        Mockito.when(mockEmployeeRepository.findById(1L)).thenReturn(
                Optional.of(Employee.builder().id(1L).firstName("John").lastName("Doe").build()));

        Optional<EmployeeDTO> employee = employeeService.findById(1L);
        assertThat(employee).isPresent();
        assertThat(employee.get().getFirstName()).isEqualTo("John");
    }

    @Test
    public void testSave() {
        Employee employee = Employee.builder().id(1L).firstName("John").lastName("Doe").build();
        Mockito.when(mockEmployeeRepository.save(any(Employee.class))).thenReturn(employee);

        EmployeeDTO employeeDTO = EmployeeDTO.builder().firstName("John").lastName("Doe").build();
        EmployeeDTO savedEmployee = employeeService.save(employeeDTO);
        assertThat(savedEmployee.getFirstName()).isEqualTo("John");
    }

    @Test
    public void testSaveWithDepartment() {
        Department department = Department.builder().id(10L).name("abc").build();
        Employee employee = Employee.builder().id(1L).firstName("John").lastName("Doe").department(department).build();
        Mockito.when(mockDepartmentRepository.findById(10L)).thenReturn(Optional.ofNullable(department));
        Mockito.when(mockEmployeeRepository.save(any(Employee.class))).thenReturn(employee);

        EmployeeDTO employeeDTO = EmployeeDTO.builder().firstName("John").lastName("Doe").departmentId(department.getId()).build();
        EmployeeDTO savedEmployee = employeeService.save(employeeDTO);
        assertThat(savedEmployee.getFirstName()).isEqualTo("John");
        assertThat(savedEmployee.getDepartmentId()).isEqualTo(department.getId());
    }

    @Test
    public void testSaveWithAddress() {
        Address address = Address.builder().id(10L).street("abc street").city("abc city").state("abc state").zipCode("12345").build();
        Employee employee = Employee.builder().id(1L).firstName("John").lastName("Doe").address(address).build();
        Mockito.when(mockAddressRepository.findById(10L)).thenReturn(Optional.ofNullable(address));
        Mockito.when(mockEmployeeRepository.save(any(Employee.class))).thenReturn(employee);

        EmployeeDTO employeeDTO = EmployeeDTO.builder().firstName("John").lastName("Doe").addressId(address.getId()).build();
        EmployeeDTO savedEmployee = employeeService.save(employeeDTO);
        assertThat(savedEmployee.getFirstName()).isEqualTo("John");
        assertThat(savedEmployee.getAddressId()).isEqualTo(address.getId());
    }

    @Test
    public void testSaveWithWorkGroup() {
        List<WorkGroup> workGroupList = new ArrayList<>();
        WorkGroup workGroup1 = WorkGroup.builder().id(10L).name("abc").build();
        WorkGroup workGroup2 = WorkGroup.builder().id(20L).name("def").build();
        workGroupList.addAll(Arrays.asList(workGroup1, workGroup2));
        Employee employee = Employee.builder().id(1L).firstName("John").lastName("Doe").workGroups(workGroupList).build();
        Mockito.when(mockWorkGroupRepository.findById(10L)).thenReturn(Optional.ofNullable(workGroup1));
        Mockito.when(mockEmployeeRepository.save(any(Employee.class))).thenReturn(employee);
        List<Long> workgroupIds = new ArrayList<>();
        workgroupIds.addAll(Arrays.asList(workGroup1.getId(), workGroup2.getId()));
        EmployeeDTO employeeDTO = EmployeeDTO.builder().firstName("John").lastName("Doe").workGroupIds(workgroupIds).build();
        EmployeeDTO savedEmployee = employeeService.save(employeeDTO);
        assertThat(savedEmployee.getFirstName()).isEqualTo("John");
        assertThat(savedEmployee.getWorkGroupIds()).isEqualTo(workgroupIds);
    }

    @Test
    public void testDeleteById() {
        Mockito.doNothing().when(mockEmployeeRepository).deleteById(anyLong());
        employeeService.deleteById(10L);
        Mockito.verify(mockEmployeeRepository, times(1)).deleteById(10L);
    }

    @Test
    public void testAssociateEmployeeWithAddress() {
        Employee employee = Employee.builder().id(1L).firstName("John").lastName("Doe").build();
        Address address = Address.builder().id(10L).street("abc street").city("abc city").state("abc state").zipCode("123456").build();
        Mockito.when(mockEmployeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        Mockito.when(mockAddressRepository.findById(10L)).thenReturn(Optional.of(address));
        employeeService.associateEmployeeWithAddress(employee.getId(), address.getId());
        assertThat(employee.getAddress().getZipCode()).isEqualTo(address.getZipCode());
        assertThat(address.getEmployee().getFirstName()).isEqualTo(employee.getFirstName());
    }

    @Test
    public void testAssociateEmployeeWithDepartment() {
        Employee employee = Employee.builder().id(1L).firstName("John").lastName("Doe").build();
        Department department = Department.builder().id(10L).name("abc").build();
        Mockito.when(mockEmployeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        Mockito.when(mockDepartmentRepository.findById(10L)).thenReturn(Optional.of(department));
        employeeService.addEmployeeToDepartment(employee.getId(), department.getId());
        assertThat(employee.getDepartment().getName()).isEqualTo(department.getName());
    }

    @Test
    public void testAssociateEmployeeWithWorkGroup() {
        Employee employee = Employee.builder().id(1L).firstName("John").lastName("Doe").build();

        WorkGroup workGroup1 = WorkGroup.builder().id(10L).name("abc").employees(new ArrayList<>()).build();
        WorkGroup workGroup2 = WorkGroup.builder().id(20L).name("def").employees(new ArrayList<>()).build();

        List<WorkGroup> workGroupList = Arrays.asList(workGroup1,workGroup2);
        List<Long> workGroupIdList = Arrays.asList(workGroup1.getId(),workGroup2.getId());

        Mockito.when(mockEmployeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        Mockito.when(mockWorkGroupRepository.findAllById(workGroupIdList)).thenReturn(workGroupList);


        employeeService.addEmployeeToWorkGroups(employee.getId(), workGroupIdList);

        assertThat(employee.getWorkGroups()).contains(workGroup1, workGroup2);
        assertThat(workGroup1.getEmployees()).contains(employee);
        assertThat(workGroup2.getEmployees()).contains(employee);
    }


}
