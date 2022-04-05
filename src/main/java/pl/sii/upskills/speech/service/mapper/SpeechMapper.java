package pl.sii.upskills.speech.service.mapper;

import pl.sii.upskills.conference.persistence.TimeSlotVO;
import pl.sii.upskills.speech.persistence.Speech;
import pl.sii.upskills.speech.service.model.SpeechInput;

import java.util.function.BiFunction;

public class SpeechMapper implements BiFunction<Speech, SpeechInput, Speech> {

    @Override
    public Speech apply(Speech speech, SpeechInput speechInput) {
        speech.setTitle(speechInput.getTitle());
        speech.setTimeSlotVO((TimeSlotVO) speechInput.getTimeSlot());
        return speech;
    }
}
