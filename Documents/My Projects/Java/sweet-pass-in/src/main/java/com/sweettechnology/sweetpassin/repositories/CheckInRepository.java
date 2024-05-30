package com.sweettechnology.sweetpassin.repositories;

import com.sweettechnology.sweetpassin.domain.checkin.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CheckInRepository extends JpaRepository<CheckIn, Integer> {
    public Optional<CheckIn> findByAttendeeId(String attendeeId);
}
