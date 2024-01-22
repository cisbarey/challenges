package com.forte.challenge.service;

import com.forte.challenge.domain.Room;
import com.forte.challenge.dto.request.ReservationRequest;
import com.forte.challenge.dto.response.ReservationResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface IReservationService {
    ReservationResponse createReservation(ReservationRequest request);
    ReservationResponse updateReservation(Long id, ReservationRequest request);
    void deleteReservation(Long id);
    ReservationResponse getReservationById(Long id);
    List<ReservationResponse> getAllReservations();
    List<Room> getAvailableRooms(LocalDateTime start, LocalDateTime end);
}
