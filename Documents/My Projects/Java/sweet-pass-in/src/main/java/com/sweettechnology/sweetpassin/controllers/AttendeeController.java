package com.sweettechnology.sweetpassin.controllers;

import com.sweettechnology.sweetpassin.dto.attendee.AttendeeBadgeResponseDTO;
import com.sweettechnology.sweetpassin.services.AttendeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/attendees")
@RequiredArgsConstructor
public class AttendeeController {
    private final AttendeeService attendeeService;
    @GetMapping("/{attendeeId}/badge")
    public ResponseEntity<AttendeeBadgeResponseDTO> getAttendeeBadge(@PathVariable String attendeeId, UriComponentsBuilder uriComponentsBuilder){
        AttendeeBadgeResponseDTO response = this.attendeeService.getAttendeeBadge(attendeeId,uriComponentsBuilder);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/{attendeeId}/check-in")
    public ResponseEntity checkIn(@PathVariable String attendeeId, UriComponentsBuilder uriComponentsBuilder){
        this.attendeeService.CheckInAttendee(attendeeId);

        var uri = uriComponentsBuilder.path("/{attendeeId}/badge").buildAndExpand(attendeeId).toUri();

        return ResponseEntity.created(uri).build();

    }
}
