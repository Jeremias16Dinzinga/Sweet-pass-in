package com.sweettechnology.sweetpassin.services;

import com.sweettechnology.sweetpassin.domain.attendee.Attendee;
import com.sweettechnology.sweetpassin.domain.attendee.exceptions.AttendeeAlreadyExistException;
import com.sweettechnology.sweetpassin.domain.attendee.exceptions.AttendeeNotFoundException;
import com.sweettechnology.sweetpassin.domain.checkin.CheckIn;
import com.sweettechnology.sweetpassin.dto.attendee.AttendeeBadgeResponseDTO;
import com.sweettechnology.sweetpassin.dto.attendee.AttendeeDetailDTO;
import com.sweettechnology.sweetpassin.dto.attendee.AttendeesListResponseDTO;
import com.sweettechnology.sweetpassin.dto.attendee.AttendeeBadgeDTO;
import com.sweettechnology.sweetpassin.repositories.AttendeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendeeService {

    private final AttendeeRepository attendeeRepository;
    private final CheckInService checkInService;

    public List<Attendee> getAllAttendeesFromEvent(String eventId){
        return this.attendeeRepository.findByEventId(eventId);
    }

    public AttendeesListResponseDTO getEventsAttendee (String eventId){
         List<Attendee> attendeeList = this.getAllAttendeesFromEvent(eventId);

         List<AttendeeDetailDTO> attendeeDetailDTOList = attendeeList.stream().map(attendee -> {
             Optional<CheckIn> checkIn = this.checkInService.getCheckIn(attendee.getId());
             LocalDateTime checkedInAt = checkIn.<LocalDateTime>map(CheckIn::getCreatedAt).orElse(null);
             return new AttendeeDetailDTO(attendee.getId(), attendee.getName(),attendee.getEmail(), attendee.getCreatedAt(), checkedInAt);
         }).toList();
         return new AttendeesListResponseDTO(attendeeDetailDTOList);
    }
    public Attendee registerAttendee(Attendee attendee){
        this.attendeeRepository.save(attendee);
        return attendee;
    }
    public void verifyAttendeeSubscription(String email, String eventId){
        Optional<Attendee> isAttendeeRegistered = this.attendeeRepository.findByEventIdAndEmail(eventId,email);
        if(isAttendeeRegistered.isPresent()) throw new AttendeeAlreadyExistException("Is attendee already registered");
    }

    public void CheckInAttendee(String attendeeId){
        Attendee attendee = this.getAttendee(attendeeId);
        this.checkInService.registerCheckIn(attendee);
    }
    private Attendee getAttendee(String attendeeId){
        return this.attendeeRepository.findById(attendeeId).orElseThrow(()-> new AttendeeNotFoundException("Attendee not found with ID: "+attendeeId));
    }
    public AttendeeBadgeResponseDTO getAttendeeBadge(String attendeeId, UriComponentsBuilder uriComponentsBuilder){
        Attendee attendee = this.getAttendee(attendeeId);

        var uri = uriComponentsBuilder.path("attendees/{attendeeId}/check-in").buildAndExpand(attendeeId).toUri().toString();

        AttendeeBadgeDTO attendeeBadgeDTO = new AttendeeBadgeDTO(attendee.getName(), attendee.getEmail(),uri, attendee.getEvent().getId());
        return new AttendeeBadgeResponseDTO(attendeeBadgeDTO);
    }
}
