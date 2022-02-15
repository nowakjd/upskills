package pl.sii.upskills.speaker.service.mapper;

import pl.sii.upskills.speaker.persistence.Speaker;
import pl.sii.upskills.speaker.service.model.SpeakerInput;

public class SpeakerUpdateMapper  {

    public Speaker updateMapping(SpeakerInput input, Speaker speaker) {
        speaker.setFirstName(input.getFirstName());
        speaker.setLastName(input.getLastName());
        speaker.setEmail(input.getEmail());
        speaker.setPhoneNumber(input.getPhoneNumber());
        speaker.setBio(input.getBio());
        return speaker;
    }
}
