package com.sweettechnology.sweetpassin.repositories;

import com.sweettechnology.sweetpassin.domain.attendee.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttendeeRepository extends JpaRepository<Attendee, String> {
     List<Attendee> findByEventId(String eventId);
     Optional<Attendee> findByEventIdAndEmail(String eventId, String email);
}
