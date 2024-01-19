package com.forte.challenge.service.impl;

import com.forte.challenge.domain.Reservation;
import com.forte.challenge.dto.request.ReservationRequest;
import com.forte.challenge.dto.response.ReservationResponse;
import com.forte.challenge.exception.ReservationException;
import com.forte.challenge.repository.ReservationRepository;
import com.forte.challenge.service.IReservationService;
import com.forte.challenge.helper.ReservationHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService implements IReservationService {

    private final ReservationRepository repository;
    private final ReservationHelper helper;

    public ReservationService(ReservationRepository repository,
                              ReservationHelper helper) {
        this.repository = repository;
        this.helper = helper;
    }

    @Override
    public ReservationResponse createReservation(ReservationRequest request) {
        Reservation newReservation = this.helper.convertToEntity(request);

        this.validateReservationDates(newReservation);
        this.checkForOverlappingReservations(newReservation);

        Reservation savedReservation = this.repository.save(newReservation);
        return this.helper.convertToResponseDTO(savedReservation);
    }

    @Override
    public ReservationResponse updateReservation(Long id, ReservationRequest request) {
        Reservation existingReservation = this.repository.findById(id)
                .orElseThrow(() -> new ReservationException("Reservation not found with id: " + id));

        if (request.getStartDateTime() != null) {
            existingReservation.setStartDateTime(request.getStartDateTime());
        }
        if (request.getEndDateTime() != null) {
            existingReservation.setEndDateTime(request.getEndDateTime());
        }
        if (request.getCustomerName() != null) {
            existingReservation.setCustomerName(request.getCustomerName());
        }

        this.validateReservationDates(existingReservation);
        this.checkForOverlappingReservations(existingReservation);

        Reservation updatedReservation = this.repository.save(existingReservation);
        return this.helper.convertToResponseDTO(updatedReservation);
    }

    @Override
    public void deleteReservation(Long id) {
        Reservation reservationToDelete = this.repository.findById(id)
                .orElseThrow(() -> new ReservationException("Reservation not found with id: " + id));

        this.repository.delete(reservationToDelete);
    }

    @Override
    public ReservationResponse getReservationById(Long id) {
        Reservation reservation = this.repository.findById(id)
                .orElseThrow(() -> new ReservationException("Reservation not found with id: " + id));

        return this.helper.convertToResponseDTO(reservation);
    }

    @Override
    public List<ReservationResponse> getAllReservations() {
        List<Reservation> reservations = this.repository.findAll();
        return reservations.stream()
                .map(this.helper::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    private void validateReservationDates(Reservation reservation) {
        LocalDateTime start = reservation.getStartDateTime();
        LocalDateTime end = reservation.getEndDateTime();

        if (start.isAfter(end) || start.isEqual(end) || start.isBefore(LocalDateTime.now())) {
            throw new ReservationException("Invalid reservation dates.");
        }
    }

    private void checkForOverlappingReservations(Reservation reservation) {
        List<Reservation> existingReservations = this.repository.findByRoomId(reservation.getRoom().getId());

        boolean overlap = existingReservations.stream().anyMatch(existing ->
                existing.getStartDateTime().isBefore(reservation.getEndDateTime()) &&
                        existing.getEndDateTime().isAfter(reservation.getStartDateTime()));

        if (overlap) {
            throw new ReservationException("The reservation overlaps with an existing reservation.");
        }
    }
}
