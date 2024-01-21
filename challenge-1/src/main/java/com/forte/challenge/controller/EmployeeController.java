package com.forte.challenge.controller;

import com.forte.challenge.dto.request.EmployeeRequest;
import com.forte.challenge.dto.request.EmployeeSearchCriteriaRequest;
import com.forte.challenge.dto.response.ApiForteResponse;
import com.forte.challenge.dto.response.EmployeeResponse;
import com.forte.challenge.service.IEmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Empleados", description = "Servicio para la gestión integral de empleados")
@SecurityRequirement(name = "basicAuth")
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final IEmployeeService service;

    public EmployeeController(IEmployeeService service) {
        this.service = service;
    }

    @Operation(summary = "Obtener empleado por ID", description = "Devuelve los detalles de un empleado específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleado encontrado"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado", content = @Content(schema = @Schema(implementation = ApiForteResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error en el servidor", content = @Content(schema = @Schema(implementation = ApiForteResponse.class)))
    })
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable Long id) {
        EmployeeResponse employee = this.service.getEmployeeById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @Operation(summary = "Crear empleado", description = "Crea un empleado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleado creado"),
            @ApiResponse(responseCode = "500", description = "Error en el servidor", content = @Content(schema = @Schema(implementation = ApiForteResponse.class)))
    })
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<EmployeeResponse> addEmployee(@RequestBody EmployeeRequest request) {
        EmployeeResponse newEmployee = this.service.saveEmployee(request);
        return new ResponseEntity<>(newEmployee, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar empleado por ID", description = "Actualiza el empleado por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleado actualizado"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado", content = @Content(schema = @Schema(implementation = ApiForteResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error en el servidor", content = @Content(schema = @Schema(implementation = ApiForteResponse.class)))
    })
    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable Long id, @RequestBody EmployeeRequest request) {
        EmployeeResponse updatedEmployee = this.service.updateEmployee(request, id);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }

    @Operation(summary = "Eliminar empleado por ID", description = "Elimina el empleado por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleado eliminado"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado", content = @Content(schema = @Schema(implementation = ApiForteResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error en el servidor", content = @Content(schema = @Schema(implementation = ApiForteResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        this.service.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Obtener empleados", description = "Devuelve los detalles de todos los empleados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleados encontrados"),
            @ApiResponse(responseCode = "500", description = "Error en el servidor", content = @Content(schema = @Schema(implementation = ApiForteResponse.class)))
    })
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {
        List<EmployeeResponse> employees = this.service.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @Operation(summary = "Búsqueda de empleados", description = "Devuelve los detalles de empleados por diferentes criterios como nombre, cargo, departamento, etc...")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleados encontrados"),
            @ApiResponse(responseCode = "500", description = "Error en el servidor", content = @Content(schema = @Schema(implementation = ApiForteResponse.class)))
    })
    @GetMapping(value = "/search", produces = "application/json")
    public ResponseEntity<List<EmployeeResponse>> searchEmployees(EmployeeSearchCriteriaRequest criteria) {
        List<EmployeeResponse> employees = this.service.searchEmployees(criteria);
        return ResponseEntity.ok(employees);
    }

}
