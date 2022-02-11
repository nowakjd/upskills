package pl.sii.upskills.conference.persistence;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class TimeSlot {

private LocalDateTime startDate;
private LocalDateTime endDate;
}
