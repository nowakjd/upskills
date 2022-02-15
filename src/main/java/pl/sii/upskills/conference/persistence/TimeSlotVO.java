package pl.sii.upskills.conference.persistence;

import pl.sii.upskills.conference.service.model.TimeSlot;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class TimeSlotVO implements TimeSlot {

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public TimeSlotVO() {
    }

    public TimeSlotVO(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }
}
