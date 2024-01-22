package com.forte.challenge.repository;

import com.forte.challenge.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("SELECT r FROM Room r WHERE r.id NOT IN (" +
            "SELECT res.room.id FROM Reservation res WHERE res.startDateTime < :endDateTime " +
            "AND res.endDateTime > :startDateTime)")
    List<Room> findAvailableRooms(LocalDateTime startDateTime, LocalDateTime endDateTime);

}
