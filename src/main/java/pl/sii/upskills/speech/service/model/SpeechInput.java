package pl.sii.upskills.speech.service.model;

import pl.sii.upskills.conference.persistence.TimeSlotVO;

public class SpeechInput {

    private final String title;
    private final TimeSlotVO timeSlotVO;

    public SpeechInput(String title, TimeSlotVO timeSlotVO) {
        this.title = title;
        this.timeSlotVO = timeSlotVO;
    }

    public String getTitle() {
        return title;
    }

    public TimeSlotVO getTimeSlot() {
        return timeSlotVO;
    }
}
