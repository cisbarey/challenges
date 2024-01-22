package com.forte.challenge.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationResponse {

    @Schema(description = "Identificador único de la reserva (clave primaria)")
    private Long id;

    @Schema(description = "Fecha y hora de inicio de la reserva")
    private LocalDateTime startDateTime;

    @Schema(description = "Fecha y hora de finalización de la reserva")
    private LocalDateTime endDateTime;

    @Schema(description = "Identificador único de la habitación o sala reservada")
    private Long roomId;

    @Schema(description = "Nombre del cliente que realiza la reserva")
    private String customerName;

    @Schema(description = "Estado actual de la reserva (confirmada, pendiente, cancelada, etc.)")
    private String status;
}
