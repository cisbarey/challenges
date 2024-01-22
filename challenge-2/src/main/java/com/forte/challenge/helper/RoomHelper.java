package com.forte.challenge.helper;

import com.forte.challenge.domain.Room;
import com.forte.challenge.dto.response.RoomResponse;
import org.springframework.stereotype.Component;

@Component
public class RoomHelper {

    public RoomResponse toResponse(Room room) {
        return RoomResponse.builder()
                .id(room.getId())
                .name(room.getName())
                .build();
    }
}
