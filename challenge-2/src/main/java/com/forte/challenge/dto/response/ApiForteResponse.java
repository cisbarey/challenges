package com.forte.challenge.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiForteResponse {

    @Schema(description = "Indica un mensaje para dar claridad de lo que ocurre con la transacción")
    private String message;

    @Schema(description = "Indica el status de la transacción")
    private Integer status;

    @Schema(description = "Indica la fecha de respuesta del servicio")
    private LocalDateTime timestamp;

}
