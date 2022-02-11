package pl.sii.upskills.conference.service.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import pl.sii.upskills.conference.persistence.TimeSlotDAO;

@JsonDeserialize(as = TimeSlotDAO.class)
public interface TimeSlot {
}
