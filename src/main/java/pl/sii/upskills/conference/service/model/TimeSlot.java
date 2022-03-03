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
        // TODO - null handling - hate Optionals
        return Duration.between(getStartDate(), getEndDate());
    }

    default boolean doesntCollide(TimeSlot timeSlotToCompare) {
        if (getStartDate().isAfter(timeSlotToCompare.getEndDate())) {
            return true;
        }

        if (getEndDate().isBefore(timeSlotToCompare.getStartDate())) {
            return true;
        }

        return false;
    }
}
