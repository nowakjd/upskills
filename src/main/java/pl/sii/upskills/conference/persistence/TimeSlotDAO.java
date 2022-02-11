package pl.sii.upskills.conference.persistence;

import pl.sii.upskills.conference.service.model.TimeSlot;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class TimeSlotDAO implements TimeSlot {

private LocalDateTime startDate;
private LocalDateTime endDate;

    public TimeSlotDAO() {
    }

    public TimeSlotDAO(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
