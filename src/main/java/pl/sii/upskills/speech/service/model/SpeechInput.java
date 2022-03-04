package pl.sii.upskills.speech.service.model;

import pl.sii.upskills.conference.service.model.TimeSlot;

public class SpeechInput {

    private final String title;
    private final TimeSlot timeSlot;

    public SpeechInput(String title, TimeSlot timeSlot) {
        this.title = title;
        this.timeSlot = timeSlot;
    }

    public String getTitle() {
        return title;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }
}
