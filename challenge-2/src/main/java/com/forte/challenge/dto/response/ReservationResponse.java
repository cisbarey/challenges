package com.forte.challenge.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationResponse {
    private Long id;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Long roomId;
    private String customerName;
    private String status;
}
