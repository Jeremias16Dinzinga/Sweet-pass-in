package com.sweettechnology.sweetpassin.config;

import com.sweettechnology.sweetpassin.domain.attendee.exceptions.AttendeeAlreadyExistException;
import com.sweettechnology.sweetpassin.domain.attendee.exceptions.AttendeeNotFoundException;
import com.sweettechnology.sweetpassin.domain.checkin.exceptions.CheckInAlreadyExistException;
import com.sweettechnology.sweetpassin.domain.event.exceptions.EventFullException;
import com.sweettechnology.sweetpassin.domain.event.exceptions.EventNotFoundException;
import com.sweettechnology.sweetpassin.dto.general.ErrResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class exceptionEntityHandler {
    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity handleEventNotFound(EventNotFoundException exception){
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler(AttendeeNotFoundException.class)
    public ResponseEntity handleAttendeeNotFound(AttendeeNotFoundException exception){
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler(AttendeeAlreadyExistException.class)
    public ResponseEntity handleAttendeeAlreadyExist(AttendeeAlreadyExistException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
    @ExceptionHandler(CheckInAlreadyExistException.class)
    public ResponseEntity handleCheckInAlreadyExist(CheckInAlreadyExistException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(EventFullException.class)
    public ResponseEntity handleEventFull(EventFullException exception){
        return ResponseEntity.badRequest().body(new ErrResponseDTO(exception.getMessage()));
    }
}
