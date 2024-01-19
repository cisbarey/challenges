package com.forte.challenge.helper;

import com.forte.challenge.common.ReservationStatus;
import com.forte.challenge.domain.Reservation;
import com.forte.challenge.dto.request.ReservationRequest;
import com.forte.challenge.dto.response.ReservationResponse;
import org.springframework.stereotype.Component;

@Component
public class ReservationHelper {

    public ReservationResponse convertToResponseDTO(Reservation reservation) {
        return ReservationResponse.builder()
                .id(reservation.getId())
                .startDateTime(reservation.getStartDateTime())
                .endDateTime(reservation.getEndDateTime())
                .roomId(reservation.getRoom().getId())
                .customerName(reservation.getCustomerName())
                .status(reservation.getStatus().name())
                .build();
    }

    public Reservation convertToEntity(ReservationRequest request) {
        return Reservation.builder()
                .startDateTime(request.getStartDateTime())
                .endDateTime(request.getEndDateTime())
                // .room(buscarRoomPorId(request.getRoomId()))
                .customerName(request.getCustomerName())
                .status(ReservationStatus.PENDING)
                .build();
    }

}
