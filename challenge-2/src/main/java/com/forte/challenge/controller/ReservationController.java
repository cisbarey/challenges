package com.forte.challenge.controller;

import com.forte.challenge.dto.request.ReservationRequest;
import com.forte.challenge.dto.response.ApiForteResponse;
import com.forte.challenge.dto.response.ReservationResponse;
import com.forte.challenge.dto.response.RoomResponse;
import com.forte.challenge.service.IReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/reservations")
@Tag(name = "Reservaciones", description = "API para gestión de reservaciones")
@SecurityRequirement(name = "basicAuth")
public class ReservationController {

    private final IReservationService service;

    public ReservationController(IReservationService service) {
        this.service = service;
    }

    @Operation(summary = "Crea una nueva reservación",
            description = "Crea una nueva reservación basándose en los datos proporcionados",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Reservación creada exitosamente", content = @Content(schema = @Schema(implementation = ReservationResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos para la reservación", content = @Content(schema = @Schema(implementation = ApiForteResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Error en el servidor", content = @Content(schema = @Schema(implementation = ApiForteResponse.class)))
    })
    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationRequest request) {
        ReservationResponse newReservation = this.service.createReservation(request);
        return new ResponseEntity<>(newReservation, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtiene una reservación por ID",
            description = "Obtiene los detalles de una reservación específica proporcionando su ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reservación encontrada", content = @Content(schema = @Schema(implementation = ReservationResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Reservación no encontrada", content = @Content(schema = @Schema(implementation = ApiForteResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Error en el servidor", content = @Content(schema = @Schema(implementation = ApiForteResponse.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> getReservationById(@PathVariable Long id) {
        ReservationResponse reservation = this.service.getReservationById(id);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    @Operation(summary = "Actualiza una reservación existente",
            description = "Actualiza los detalles de una reservación existente basándose en el ID y los datos proporcionados",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reservación actualizada exitosamente",  content = @Content(schema = @Schema(implementation = ReservationResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos para la actualización de la reservación", content = @Content(schema = @Schema(implementation = ApiForteResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Reservación no encontrada", content = @Content(schema = @Schema(implementation = ApiForteResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Error en el servidor", content = @Content(schema = @Schema(implementation = ApiForteResponse.class)))
            })
    @PutMapping("/{id}")
    public ResponseEntity<ReservationResponse> updateReservation(@PathVariable Long id, @RequestBody ReservationRequest request) {
        ReservationResponse updatedReservation = this.service.updateReservation(id, request);
        return new ResponseEntity<>(updatedReservation, HttpStatus.OK);
    }

    @Operation(summary = "Elimina una reservación existente",
            description = "Elimina una reservación específica proporcionando su ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reservación eliminada exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Reservación no encontrada", content = @Content(schema = @Schema(implementation = ApiForteResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Error en el servidor", content = @Content(schema = @Schema(implementation = ApiForteResponse.class)))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        this.service.deleteReservation(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Obtiene todas las reservaciones",
            description = "Devuelve una lista de todas las reservaciones existentes",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de reservaciones obtenida exitosamente", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ReservationResponse.class)))),
                    @ApiResponse(responseCode = "500", description = "Error en el servidor", content = @Content(schema = @Schema(implementation = ApiForteResponse.class)))
            })
    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getAllReservations() {
        List<ReservationResponse> reservations = this.service.getAllReservations();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene habitaciones disponibles",
            description = "Devuelve una lista de habitaciones disponibles dentro de un rango de fechas especificado",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Habitaciones disponibles encontradas", content = @Content(array = @ArraySchema(schema = @Schema(implementation = RoomResponse.class)))),
                    @ApiResponse(responseCode = "400", description = "Parámetros de fecha inválidos", content = @Content(schema = @Schema(implementation = ApiForteResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Error en el servidor", content = @Content(schema = @Schema(implementation = ApiForteResponse.class)))
            })
    @GetMapping("/availability")
    public ResponseEntity<List<RoomResponse>> getAvailableRooms(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        List<RoomResponse> availableRooms = this.service.getAvailableRooms(start, end);
        return new ResponseEntity<>(availableRooms, HttpStatus.OK);
    }

}
