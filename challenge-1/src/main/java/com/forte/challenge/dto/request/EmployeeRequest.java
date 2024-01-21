package com.forte.challenge.dto.request;

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
public class EmployeeRequest {

    @Schema(description = "Nombre completo del empleado")
    private String name;

    @Schema(description = "Puesto o cargo que ocupa el empleado en la empresa")
    private String position;

    @Schema(description = "Departamento al que pertenece el empleado")
    private String department;

    @Schema(description = "Fecha en la que el empleado ingresó a la empresa")
    private LocalDate dateOfJoining;

    @Schema(description = "Salario que recibe el empleado")
    private BigDecimal salary;

    @Schema(description = "Dirección de correo electrónico única para cada empleado")
    private String email;

}
