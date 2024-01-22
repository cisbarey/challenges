package com.forte.challenge.service.impl;

import com.forte.challenge.common.ReservationStatus;
import com.forte.challenge.domain.Reservation;
import com.forte.challenge.domain.Room;
import com.forte.challenge.dto.request.ReservationRequest;
import com.forte.challenge.dto.response.ReservationResponse;
import com.forte.challenge.exception.ReservationException;
import com.forte.challenge.exception.ReservationNotFoundException;
import com.forte.challenge.exception.RoomNotFoundException;
import com.forte.challenge.repository.ReservationRepository;
import com.forte.challenge.repository.RoomRepository;
import com.forte.challenge.service.IReservationService;
import com.forte.challenge.helper.ReservationHelper;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ReservationService implements IReservationService {

    private final ReservationRepository repository;
    private final RoomRepository roomRepository;
    private final ReservationHelper helper;

    public ReservationService(ReservationRepository repository,
                              RoomRepository roomRepository,
                              ReservationHelper helper) {
        this.repository = repository;
        this.roomRepository = roomRepository;
        this.helper = helper;
    }

    @Override
    public ReservationResponse createReservation(ReservationRequest request) {
        Room room = this.roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RoomNotFoundException(request.getRoomId()));
        Reservation newReservation = this.helper.convertToEntity(request, room);
        newReservation.setStatus(ReservationStatus.RESERVED);

        this.validateReservationDates(newReservation);
        this.checkForOverlappingReservations(newReservation);

        Reservation savedReservation = this.repository.save(newReservation);
        return this.helper.convertToResponseDTO(savedReservation);
    }

    @Override
    public ReservationResponse updateReservation(Long id, ReservationRequest request) {
        Reservation existingReservation = this.repository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));

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
        this.checkForOverlappingReservations(existingReservation, id);

        Room room = this.roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RoomNotFoundException(request.getRoomId()));
        existingReservation.setRoom(room);

        Reservation updatedReservation = this.repository.save(existingReservation);
        return this.helper.convertToResponseDTO(updatedReservation);
    }

    @Override
    public void deleteReservation(Long id) {
        Reservation reservationToDelete = this.repository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));

        this.repository.delete(reservationToDelete);
    }

    @Override
    public ReservationResponse getReservationById(Long id) {
        Reservation reservation = this.repository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));

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
        this.checkForOverlappingReservations(reservation, null);
    }

    private void checkForOverlappingReservations(Reservation reservation, @Nullable Long excludeReservationId) {
        List<Reservation> existingReservations = excludeReservationId == null ?
                repository.findByRoomId(reservation.getRoom().getId()) :
                repository.findByRoomIdAndNotId(reservation.getRoom().getId(), excludeReservationId);

        boolean overlap = existingReservations.stream().anyMatch(existing ->
                existing.getStartDateTime().isBefore(reservation.getEndDateTime()) &&
                        existing.getEndDateTime().isAfter(reservation.getStartDateTime()) &&
                        !Objects.equals(existing.getId(), excludeReservationId));

        if (overlap) {
            throw new ReservationException("The reservation overlaps with an existing reservation.");
        }
    }

    @Override
    public List<Room> getAvailableRooms(LocalDateTime start, LocalDateTime end) {
        return roomRepository.findAvailableRooms(start, end);
    }
}
