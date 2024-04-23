package com.sweettechnology.sweetpassin.repositories;

import com.sweettechnology.sweetpassin.domain.checkin.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckInRepository extends JpaRepository<CheckIn, Integer> {
}
