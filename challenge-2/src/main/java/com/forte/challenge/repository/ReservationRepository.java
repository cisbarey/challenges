package com.forte.challenge.repository;

import com.forte.challenge.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByRoomId(Long roomId);
    @Query("SELECT r FROM Reservation r WHERE r.room.id = :roomId AND r.id <> :reservationId")
    List<Reservation> findByRoomIdAndNotId(@Param("roomId") Long roomId, @Param("reservationId") Long reservationId);
}
