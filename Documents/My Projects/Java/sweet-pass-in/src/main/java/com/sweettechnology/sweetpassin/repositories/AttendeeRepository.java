package com.sweettechnology.sweetpassin.repositories;

import com.sweettechnology.sweetpassin.domain.attendee.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendeeRepository extends JpaRepository<Attendee, String> {
}
