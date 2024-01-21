package com.forte.challenge.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {

    @Schema(description = "ID único del empleado")
    private Long id;

    @Schema(description = "Nombre del empleado")
    private String name;

    @Schema(description = "Posición del empleado en la empresa")
    private String position;

    @Schema(description = "Departamento del empleado")
    private String department;

    @Schema(description = "Fecha de incorporación del empleado")
    private LocalDate dateOfJoining;

    @Schema(description = "Salario que recibe el empleado")
    private BigDecimal salary;

    @Schema(description = "Dirección de correo electrónico del empleado")
    private String email;
}

