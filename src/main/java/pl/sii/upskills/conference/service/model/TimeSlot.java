package pl.sii.upskills.conference.service.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import pl.sii.upskills.conference.persistence.TimeSlotDAO;

import java.time.LocalDateTime;

@JsonDeserialize(as = TimeSlotDAO.class)
public interface TimeSlot {

    public LocalDateTime getStartDate ();
    public LocalDateTime getEndDate ();
}
