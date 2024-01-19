package com.forte.challenge.controller;

import com.forte.challenge.dto.request.EmployeeRequest;
import com.forte.challenge.dto.request.EmployeeSearchCriteriaRequest;
import com.forte.challenge.dto.response.ApiResponse;
import com.forte.challenge.dto.response.EmployeeResponse;
import com.forte.challenge.service.IEmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final IEmployeeService service;

    public EmployeeController(IEmployeeService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponse>> getEmployeeById(@PathVariable Long id) {
        EmployeeResponse employee = this.service.getEmployeeById(id);
        ApiResponse<EmployeeResponse> response = ApiResponse.<EmployeeResponse>builder()
                .success(Boolean.TRUE)
                .data(employee)
                .message("Empleado recuperado con éxito")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EmployeeResponse>> addEmployee(@RequestBody EmployeeRequest request) {
        EmployeeResponse newEmployee = this.service.saveEmployee(request);
        ApiResponse<EmployeeResponse> response = ApiResponse.<EmployeeResponse>builder()
                .success(Boolean.TRUE)
                .data(newEmployee)
                .message("Empleado agregado con éxito")
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponse>> updateEmployee(@PathVariable Long id, @RequestBody EmployeeRequest request) {
        EmployeeResponse updatedEmployee = this.service.updateEmployee(request, id);
        ApiResponse<EmployeeResponse> response = ApiResponse.<EmployeeResponse>builder()
                .success(Boolean.TRUE)
                .data(updatedEmployee)
                .message("Empleado actualizado con éxito")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEmployee(@PathVariable Long id) {
        this.service.deleteEmployee(id);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(Boolean.TRUE)
                .message("Empleado eliminado con éxito")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<EmployeeResponse>>> getAllEmployees() {
        List<EmployeeResponse> employees = this.service.getAllEmployees();
        ApiResponse<List<EmployeeResponse>> response = ApiResponse.<List<EmployeeResponse>>builder()
                .success(Boolean.TRUE)
                .data(employees)
                .message("Lista de empleados recuperada con éxito")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<EmployeeResponse>>> searchEmployees(EmployeeSearchCriteriaRequest criteria) {
        List<EmployeeResponse> employees = this.service.searchEmployees(criteria);
        ApiResponse<List<EmployeeResponse>> response = ApiResponse.<List<EmployeeResponse>>builder()
                .success(Boolean.TRUE)
                .data(employees)
                .message("Resultados de la búsqueda")
                .build();
        return ResponseEntity.ok(response);
    }

}
