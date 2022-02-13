package pl.sii.upskills.conference.service.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import pl.sii.upskills.conference.persistence.TimeSlotDAO;

/**
 Representation of period placed in time.
 */
@JsonDeserialize(as = TimeSlotDAO.class)
public interface TimeSlot {
}
