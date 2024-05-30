package com.sweettechnology.sweetpassin.dto.attendee;

public record AttendeeBadgeDTO(
        String name,
        String email,
        String checkInUrl,
        String eventId
) {
}
