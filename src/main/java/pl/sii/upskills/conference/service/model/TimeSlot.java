package pl.sii.upskills.conference.service.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import pl.sii.upskills.conference.persistence.TimeSlotVO;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Period placed in time.
 */
@JsonDeserialize(as = TimeSlotVO.class)
public interface TimeSlot {

    LocalDateTime getStartDate();

    LocalDateTime getEndDate();

    default Duration toDuration() {
        return Duration.between(getStartDate(), getEndDate());
    }

    default boolean doesntCollide(TimeSlot timeSlotToCompare) {
        return getStartDate().isAfter(timeSlotToCompare.getEndDate())
                || getEndDate().isBefore(timeSlotToCompare.getStartDate());
    }

    default boolean fitIn(TimeSlot other) {
        return !getStartDate().isBefore(other.getStartDate()) && !getEndDate().isAfter(other.getEndDate());

    }
}
