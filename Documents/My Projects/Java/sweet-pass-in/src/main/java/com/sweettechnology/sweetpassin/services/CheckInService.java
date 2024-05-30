package com.sweettechnology.sweetpassin.services;

import com.sweettechnology.sweetpassin.domain.attendee.Attendee;
import com.sweettechnology.sweetpassin.domain.checkin.CheckIn;
import com.sweettechnology.sweetpassin.domain.checkin.exceptions.CheckInAlreadyExistException;
import com.sweettechnology.sweetpassin.repositories.CheckInRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckInService {
    private final CheckInRepository checkInRepository;

    public void registerCheckIn(Attendee attendee){

        this.verifyCheckInExist(attendee.getId());

        CheckIn checkIn = new CheckIn();
        checkIn.setAttendee(attendee);
        checkIn.setCreatedAt(LocalDateTime.now());

        checkInRepository.save(checkIn);
    }
    private void verifyCheckInExist(String attendeeId){

        Optional<CheckIn>  isCheckIn = this.getCheckIn(attendeeId);

        if(isCheckIn.isPresent()) throw  new CheckInAlreadyExistException("Attendee already checked in");
    }
    public Optional<CheckIn> getCheckIn(String attendeeId){
        return this.checkInRepository.findByAttendeeId(attendeeId);
    }
}
