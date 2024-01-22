package com.forte.challenge.service;

import com.forte.challenge.dto.request.ReservationRequest;
import com.forte.challenge.dto.response.ReservationResponse;
import com.forte.challenge.dto.response.RoomResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface IReservationService {
    ReservationResponse createReservation(ReservationRequest request);
    ReservationResponse updateReservation(Long id, ReservationRequest request);
    void deleteReservation(Long id);
    ReservationResponse getReservationById(Long id);
    List<ReservationResponse> getAllReservations();
    List<RoomResponse> getAvailableRooms(LocalDateTime start, LocalDateTime end);
}
