package com.forte.challenge.controller;

import com.forte.challenge.dto.request.ReservationRequest;
import com.forte.challenge.dto.response.ApiResponse;
import com.forte.challenge.dto.response.ReservationResponse;
import com.forte.challenge.service.IReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final IReservationService service;

    public ReservationController(IReservationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ReservationResponse>> createReservation(@RequestBody ReservationRequest request) {
        ReservationResponse newReservation = this.service.createReservation(request);
        ApiResponse<ReservationResponse> response = ApiResponse.<ReservationResponse>builder()
                .success(Boolean.TRUE)
                .data(newReservation)
                .message("Reserva creada exitosamente")
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReservationResponse>> getReservationById(@PathVariable Long id) {
        ReservationResponse reservation = this.service.getReservationById(id);
        ApiResponse<ReservationResponse> response = ApiResponse.<ReservationResponse>builder()
                .success(Boolean.TRUE)
                .data(reservation)
                .message("Reserva obtenida exitosamente")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ReservationResponse>> updateReservation(@PathVariable Long id, @RequestBody ReservationRequest request) {
        ReservationResponse updatedReservation = this.service.updateReservation(id, request);
        ApiResponse<ReservationResponse> response = ApiResponse.<ReservationResponse>builder()
                .success(Boolean.TRUE)
                .data(updatedReservation)
                .message("Reserva actualizada exitosamente")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteReservation(@PathVariable Long id) {
        this.service.deleteReservation(id);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(Boolean.TRUE)
                .message("Reserva eliminada exitosamente")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ReservationResponse>>> getAllReservations() {
        List<ReservationResponse> reservations = this.service.getAllReservations();
        ApiResponse<List<ReservationResponse>> response = ApiResponse.<List<ReservationResponse>>builder()
                .success(Boolean.TRUE)
                .data(reservations)
                .message("Todas las reservas obtenidas exitosamente")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
