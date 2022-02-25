package pl.sii.upskills.speech.service.model;

import pl.sii.upskills.conference.service.model.TimeSlot;
import pl.sii.upskills.speaker.service.model.SpeakerOutput;

import java.util.Objects;
import java.util.Set;

public class SpeechOutput {
    private final String title;
    private final TimeSlot timeSlot;
    private final Set<SpeakerOutput> speakerOutputSet;
    private final Long id;

    public SpeechOutput(String title, TimeSlot timeSlot, Set<SpeakerOutput> speakerOutputSet, Long id) {
        this.title = title;
        this.timeSlot = timeSlot;
        this.speakerOutputSet = speakerOutputSet;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public Set<SpeakerOutput> getSpeakerOutputSet() {
        return speakerOutputSet;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpeechOutput that = (SpeechOutput) o;
        return title.equals(that.title) && timeSlot.equals(that.timeSlot) && speakerOutputSet.equals(
                that.speakerOutputSet)
                && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, timeSlot, speakerOutputSet, id);
    }
}
