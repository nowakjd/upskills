package pl.sii.upskills.conference.persistence;

import pl.sii.upskills.conference.service.model.TimeSlot;
import pl.sii.upskills.configuration.Generated;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;
import java.util.Objects;

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

    @Generated
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeSlotVO that = (TimeSlotVO) o;

        if (!Objects.equals(startDate, that.startDate)) return false;
        return Objects.equals(endDate, that.endDate);
    }

    @Generated
    @Override
    public int hashCode() {
        int result = startDate != null ? startDate.hashCode() : 0;
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        return result;
    }
}
