package com.sweettechnology.sweetpassin.services;

import com.sweettechnology.sweetpassin.domain.attendee.Attendee;
import com.sweettechnology.sweetpassin.domain.checkin.CheckIn;
import com.sweettechnology.sweetpassin.dto.attendee.AttendeeDetailDTO;
import com.sweettechnology.sweetpassin.dto.attendee.AttendeesListResponseDTO;
import com.sweettechnology.sweetpassin.repositories.AttendeeRepository;
import com.sweettechnology.sweetpassin.repositories.CheckInRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendeeService {

    private final AttendeeRepository attendeeRepository;
    private final CheckInRepository checkInRepository;

    public List<Attendee> getAllAttendeesFromEvent(String eventId){
        return this.attendeeRepository.findByEventId(eventId);
    }

    public AttendeesListResponseDTO getEventsAttendee (String eventId){
         List<Attendee> attendeeList = this.getAllAttendeesFromEvent(eventId);

         List<AttendeeDetailDTO> attendeeDetailDTOList = attendeeList.stream().map(attendee -> {
             Optional<CheckIn> checkIn = this.checkInRepository.findByAttendeeId(attendee.getId());
             LocalDateTime checkedInAt = checkIn.<LocalDateTime>map(CheckIn::getCreatedAt).orElse(null);
             return new AttendeeDetailDTO(attendee.getId(), attendee.getName(),attendee.getEmail(), attendee.getCreatedAt(), checkedInAt);
         }).toList();
         return new AttendeesListResponseDTO(attendeeDetailDTOList);
    }
}
