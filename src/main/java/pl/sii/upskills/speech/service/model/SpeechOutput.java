package pl.sii.upskills.speech.service.model;

import pl.sii.upskills.conference.persistence.TimeSlotVO;
import pl.sii.upskills.speaker.persistence.Speaker;

import java.util.Objects;
import java.util.Set;

public class SpeechOutput {
    private final String title;
    private final TimeSlotVO timeSlotVO;
    private final Set<Speaker> speakerSet;
    private final Long id;

    public SpeechOutput(String title, TimeSlotVO timeSlotVO, Set<Speaker> speakerSet, Long id) {
        this.title = title;
        this.timeSlotVO = timeSlotVO;
        this.speakerSet = speakerSet;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public TimeSlotVO getTimeSlotVO() {
        return timeSlotVO;
    }

    public Set<Speaker> getSpeakerSet() {
        return speakerSet;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpeechOutput that = (SpeechOutput) o;
        return title.equals(that.title) && timeSlotVO.equals(that.timeSlotVO) && speakerSet.equals(that.speakerSet)
                && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, timeSlotVO, speakerSet, id);
    }
}
