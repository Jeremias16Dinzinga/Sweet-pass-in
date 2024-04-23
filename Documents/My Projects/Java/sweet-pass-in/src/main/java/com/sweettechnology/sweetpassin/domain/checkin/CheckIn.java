package com.sweettechnology.sweetpassin.domain.checkin;

import com.sweettechnology.sweetpassin.domain.attendee.Attendee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name="checkins")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CheckIn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name="attendee_id", nullable = false)
    private Attendee attendee;
}
