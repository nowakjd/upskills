package pl.sii.upskills.speech.service.model;

import pl.sii.upskills.conference.service.model.TimeSlot;
import pl.sii.upskills.speaker.service.model.SpeakerOutput;

import java.util.Objects;
import java.util.Set;

public class SpeechOutput {
    private final String title;
    private final TimeSlot timeSlot;
    private final Set<SpeakerOutput> speakers;
    private final Long id;

    public SpeechOutput(String title, TimeSlot timeSlot, Long id, Set<SpeakerOutput> speakers) {
        this.title = title;
        this.timeSlot = timeSlot;
        this.speakers = speakers;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public Set<SpeakerOutput> getSpeakers() {
        return speakers;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpeechOutput that = (SpeechOutput) o;
        return title.equals(that.title) && timeSlot.equals(that.timeSlot) && speakers.equals(
                that.speakers)
                && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, timeSlot, speakers, id);
    }
}
