package com.sweettechnology.sweetpassin.dto.attendee;

import java.time.LocalDateTime;

public record AttendeeDetailDTO(
        String id,
        String name,
        String email,
        LocalDateTime createdAt,
        LocalDateTime checkedAt
) {
}
