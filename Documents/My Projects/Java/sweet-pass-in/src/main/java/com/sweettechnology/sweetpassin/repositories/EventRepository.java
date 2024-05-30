package com.sweettechnology.sweetpassin.repositories;

import com.sweettechnology.sweetpassin.domain.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;
public interface EventRepository extends JpaRepository<Event, String> {

}
