package pl.sii.upskills.conference.service.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import pl.sii.upskills.conference.persistence.TimeSlotVO;

import java.time.LocalDateTime;

/**
 * Period placed in time.
 */
@JsonDeserialize(as = TimeSlotVO.class)
public interface TimeSlot {

    LocalDateTime getStartDate();

    LocalDateTime getEndDate();
}
