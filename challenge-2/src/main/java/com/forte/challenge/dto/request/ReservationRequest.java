package com.forte.challenge.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationRequest {

    @Schema(description = "Fecha y hora de inicio de la reserva")
    private LocalDateTime startDateTime;

    @Schema(description = "Fecha y hora de finalización de la reserva")
    private LocalDateTime endDateTime;

    @Schema(description = "Identificador único de la habitación o sala reservada")
    private Long roomId;

    @Schema(description = "Nombre del cliente que realiza la reserva")
    private String customerName;
}
