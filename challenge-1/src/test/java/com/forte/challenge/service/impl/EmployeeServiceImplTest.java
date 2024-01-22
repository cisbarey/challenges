package com.forte.challenge.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.forte.challenge.domain.Employee;
import com.forte.challenge.dto.request.EmployeeRequest;
import com.forte.challenge.dto.request.EmployeeSearchCriteriaRequest;
import com.forte.challenge.dto.response.EmployeeResponse;
import com.forte.challenge.helper.EmployeeHelper;
import com.forte.challenge.repository.IEmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.domain.Specification;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private IEmployeeRepository employeeRepository;
    @Mock
    private EmployeeHelper employeeHelper;
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void init() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void createEmployee_fromJson() throws Exception {
        File jsonFile = new ClassPathResource("employee.json").getFile();
        String json = new String(Files.readAllBytes(jsonFile.toPath()));

        EmployeeRequest employeeRequest = this.objectMapper.readValue(json, EmployeeRequest.class);

        when(this.employeeHelper.convertToEntity(any(EmployeeRequest.class))).thenReturn(new Employee());
        when(this.employeeRepository.save(any(Employee.class))).thenAnswer(invocation -> invocation.getArgument(0));

        this.employeeService.saveEmployee(employeeRequest);

        verify(this.employeeRepository).save(any(Employee.class));
    }

    @Test
    void getAllEmployees() throws Exception {
        File jsonFile = new ClassPathResource("employees_list.json").getFile();
        String json = new String(Files.readAllBytes(jsonFile.toPath()));

        List<Employee> employees = this.objectMapper.readValue(json, new TypeReference<>() {});

        when(this.employeeRepository.findAll()).thenReturn(employees);
        when(this.employeeHelper.convertToDTO(any(Employee.class)))
                .thenAnswer(invocation -> {
                    Employee emp = invocation.getArgument(0);
                    return EmployeeResponse.builder()
                            .name(emp.getName())
                            .position(emp.getPosition())
                            .department(emp.getDepartment())
                            .dateOfJoining(emp.getDateOfJoining())
                            .salary(emp.getSalary())
                            .email(emp.getEmail())
                            .build();
                });

        List<EmployeeResponse> result = this.employeeService.getAllEmployees();

        assertNotNull(result, "La lista de empleados no debe ser nula");
        assertEquals(employees.size(), result.size(), "El tamaño de la lista de empleados debe coincidir con el esperado");

        verify(this.employeeRepository).findAll();
        verify(this.employeeHelper, times(employees.size())).convertToDTO(any(Employee.class));
    }

    @Test
    void getEmployeeById_Success() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("John Doe");
        employee.setPosition("Developer");
        employee.setDepartment("IT");
        employee.setDateOfJoining(LocalDate.of(2022, 1, 1));
        employee.setSalary(new BigDecimal("100000"));
        employee.setEmail("john.doe@forte.com");

        when(this.employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(this.employeeHelper.convertToDTO(any(Employee.class))).thenReturn(EmployeeResponse.builder()
                        .name("John Doe")
                        .position("Developer")
                        .department("IT")
                        .dateOfJoining(LocalDate.of(2022, 1, 1))
                        .salary(new BigDecimal("100000"))
                        .email("john.doe@forte.com")
                        .build());

        EmployeeResponse result = this.employeeService.getEmployeeById(1L);

        assertNotNull(result, "El empleado no debe ser nulo");
        assertEquals(employee.getName(), result.getName(), "El nombre del empleado debe coincidir");
        assertEquals(employee.getPosition(), result.getPosition(), "El cargo del empleado debe coincidir");
        assertEquals(employee.getDepartment(), result.getDepartment(), "El departamento del empleado debe coincidir");
        assertEquals(employee.getDateOfJoining(), result.getDateOfJoining(), "La fecha de ingreso del empleado debe coincidir");
        assertEquals(employee.getSalary(), result.getSalary(), "El salario del empleado debe coincidir");
        assertEquals(employee.getEmail(), result.getEmail(), "El correo electrónico del empleado debe coincidir");

        verify(this.employeeRepository).findById(1L);
        verify(this.employeeHelper).convertToDTO(any(Employee.class));
    }

    @Test
    void updateEmployee_Success() {
        Long existingEmployeeId = 1L;
        Employee existingEmployee = new Employee(existingEmployeeId,
                "John Doe",
                "Developer",
                "IT",
                LocalDate.of(2022, 1, 1),
                new BigDecimal("100000"),
                "john.doe@forte.com");
        EmployeeRequest updateRequest = EmployeeRequest.builder()
                .name("Jane Doe")
                .position("Senior Developer")
                .department("IT")
                .dateOfJoining(LocalDate.of(2022, 1, 1))
                .salary(new BigDecimal("120000"))
                .email("jane.doe@forte.com")
                .build();

        when(this.employeeRepository.findById(existingEmployeeId)).thenReturn(Optional.of(existingEmployee));
        when(this.employeeRepository.save(any(Employee.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(this.employeeHelper.convertToDTO(any(Employee.class))).thenAnswer(invocation -> {
            Employee emp = invocation.getArgument(0);
            return EmployeeResponse.builder()
                    .name(emp.getName())
                    .position(emp.getPosition())
                    .department(emp.getDepartment())
                    .dateOfJoining(emp.getDateOfJoining())
                    .salary(emp.getSalary())
                    .email(emp.getEmail())
                    .build();
        });

        EmployeeResponse updatedEmployeeResponse = this.employeeService.updateEmployee(updateRequest, existingEmployeeId);

        assertNotNull(updatedEmployeeResponse, "El resultado de la actualización no debe ser nulo");
        assertEquals(updateRequest.getName(), updatedEmployeeResponse.getName(), "El nombre del empleado debe haberse actualizado");
        assertEquals(updateRequest.getPosition(), updatedEmployeeResponse.getPosition(), "El cargo del empleado debe haberse actualizado");
        assertEquals(updateRequest.getDepartment(), updatedEmployeeResponse.getDepartment(), "El departamento del empleado debe haberse actualizado");
        assertEquals(updateRequest.getDateOfJoining(), updatedEmployeeResponse.getDateOfJoining(), "La fecha de ingreso del empleado debe haberse actualizado");
        assertEquals(updateRequest.getSalary(), updatedEmployeeResponse.getSalary(), "El salario del empleado debe haberse actualizado");
        assertEquals(updateRequest.getEmail(), updatedEmployeeResponse.getEmail(), "El correo electrónico del empleado debe haberse actualizado");

        verify(this.employeeRepository).findById(existingEmployeeId);
        verify(this.employeeRepository).save(any(Employee.class));
    }

    @Test
    void deleteEmployee_Success() {
        Long employeeIdToDelete = 1L;
        Employee existingEmployee = new Employee(employeeIdToDelete,
                "John Doe",
                "Developer",
                "IT",
                LocalDate.of(2022, 1, 1),
                new BigDecimal("100000"),
                "john.doe@forte.com");

        when(this.employeeRepository.findById(employeeIdToDelete)).thenReturn(Optional.of(existingEmployee));

        this.employeeService.deleteEmployee(employeeIdToDelete);

        verify(this.employeeRepository).delete(existingEmployee);
        verify(this.employeeRepository).findById(employeeIdToDelete);
    }

    @Test
    void searchEmployees_Success() {
        EmployeeSearchCriteriaRequest criteria = new EmployeeSearchCriteriaRequest();
        criteria.setName("John");
        criteria.setDepartment("IT");

        List<Employee> matchingEmployees = List.of(
                new Employee(1L,
                        "John Doe",
                        "Developer",
                        "IT",
                        LocalDate.of(2022, 1, 1),
                        new BigDecimal("100000"),
                        "john.doe@forte.com"),
                new Employee(2L,
                        "Johnny Roe",
                        "Tester",
                        "IT",
                        LocalDate.of(2023, 1, 1),
                        new BigDecimal("90000"),
                        "johnny.roe@forte.com")
        );

        when(this.employeeRepository.findAll(any(Specification.class))).thenReturn(matchingEmployees);
        when(this.employeeHelper.convertToDTO(any(Employee.class))).thenAnswer(invocation -> {
            Employee emp = invocation.getArgument(0);
            return EmployeeResponse.builder()
                    .name(emp.getName())
                    .position(emp.getPosition())
                    .department(emp.getDepartment())
                    .dateOfJoining(emp.getDateOfJoining())
                    .salary(emp.getSalary())
                    .email(emp.getEmail())
                    .build();
        });

        List<EmployeeResponse> result = this.employeeService.searchEmployees(criteria);

        assertNotNull(result, "La lista de resultados no debe ser nula");
        assertEquals(matchingEmployees.size(), result.size(), "El tamaño de la lista de resultados debe coincidir con el esperado");

        verify(this.employeeRepository).findAll(any(Specification.class));
        verify(this.employeeHelper, times(matchingEmployees.size())).convertToDTO(any(Employee.class));
    }

}
