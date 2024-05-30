package com.sweettechnology.sweetpassin.services;

import com.sweettechnology.sweetpassin.domain.attendee.Attendee;
import com.sweettechnology.sweetpassin.domain.event.Event;
import com.sweettechnology.sweetpassin.domain.event.exceptions.EventFullException;
import com.sweettechnology.sweetpassin.domain.event.exceptions.EventNotFoundException;
import com.sweettechnology.sweetpassin.dto.attendee.AttendeeIdDTO;
import com.sweettechnology.sweetpassin.dto.attendee.AttendeeResquestDTO;
import com.sweettechnology.sweetpassin.dto.event.EventIdDTO;
import com.sweettechnology.sweetpassin.dto.event.EventRequestDTO;
import com.sweettechnology.sweetpassin.dto.event.EventResponseDTO;
import com.sweettechnology.sweetpassin.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final AttendeeService attendeeService;

    public EventResponseDTO getEventDetail(String eventId) {
        Event event = this.getEventById(eventId);
        List<Attendee> attendees = this.attendeeService.getAllAttendeesFromEvent(eventId);
        return new EventResponseDTO(event, attendees.size());
    }

    public EventIdDTO createEvent(EventRequestDTO eventDTO) {
        Event newEvent = new Event();

        newEvent.setTitle(eventDTO.title());
        newEvent.setDetails(eventDTO.details());
        newEvent.setMaximumAttendees(eventDTO.maximumAttendees());
        newEvent.setSlug(createSlug(eventDTO.title()));
        this.eventRepository.save(newEvent);
        return new EventIdDTO(newEvent.getId());
    }
    public AttendeeIdDTO registerAttendeeOnEvent(String eventId, AttendeeResquestDTO attendeeResquestDTO){
        this.attendeeService.verifyAttendeeSubscription(attendeeResquestDTO.email(),eventId);

        Event event = this.getEventById(eventId);
        List<Attendee> attendees = this.attendeeService.getAllAttendeesFromEvent(eventId);

        if(event.getMaximumAttendees()<=attendees.size()) throw new EventFullException("Event is full");

        Attendee attendee = new Attendee();
        attendee.setName(attendeeResquestDTO.name());
        attendee.setEmail(attendeeResquestDTO.email());
        attendee.setEvent(event);
        attendee.setCreatedAt(LocalDateTime.now());
        this.attendeeService.registerAttendee(attendee);

        return new AttendeeIdDTO(attendee.getId());
    }

    private Event getEventById(String eventId){
       return this.eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException("Event not found with Id:" + eventId));
    }

    private String createSlug(String text) {
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalized.replaceAll("[\\p{InCOMBINING_DIACRITICAL_MARKS}]", "")
                .replaceAll("[^\\w\\s]", "")
                .replaceAll("\\s+", "-")
                .toLowerCase();
    }
}
