package com.forte.challenge.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponse {

    @Schema(description = "Identificador único de la habitación o sala reservada")
    private Long id;

    @Schema(description = "Número o nombre de la habitación o sala reservada")
    private String name;
}
