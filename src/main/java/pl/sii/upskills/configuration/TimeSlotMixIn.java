package pl.sii.upskills.configuration;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import pl.sii.upskills.conference.persistence.TimeSlotVO;

@JsonDeserialize(as = TimeSlotVO.class)
public class TimeSlotMixIn {
}
