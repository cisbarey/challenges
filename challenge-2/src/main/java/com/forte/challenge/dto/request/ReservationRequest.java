package com.forte.challenge.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationRequest {
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Long roomId;
    private String customerName;
}
