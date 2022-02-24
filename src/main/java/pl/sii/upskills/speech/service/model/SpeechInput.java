package pl.sii.upskills.speech.service.model;

import pl.sii.upskills.conference.persistence.TimeSlotVO;
import pl.sii.upskills.speaker.persistence.Speaker;

import java.util.Set;

public class SpeechInput {

    private final String title;
    private final TimeSlotVO timeSlotVO;
    private final Set<Speaker> speakerSet;

    public SpeechInput(String title, TimeSlotVO timeSlotVO, Set<Speaker> speakerSet) {
        this.title = title;
        this.timeSlotVO = timeSlotVO;
        this.speakerSet = speakerSet;
    }

    public String getTitle() {
        return title;
    }

    public TimeSlotVO getTimeSlot() {
        return timeSlotVO;
    }

    public Set<Speaker> getSpeakerSet() {
        return speakerSet;
    }
}
