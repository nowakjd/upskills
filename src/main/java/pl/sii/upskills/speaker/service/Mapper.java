package pl.sii.upskills.speaker.service;

import pl.sii.upskills.speaker.persistence.Speaker;

public class Mapper {
    public static Speaker map(SpeakerInput input) {
        Speaker speaker = new Speaker();
        speaker.setFirstName(input.getFirstName());
        speaker.setLastName(input.getLastName());
        speaker.setEmail(input.getEmail());
        speaker.setPhoneNumber(input.getPhoneNumber());
        speaker.setBio(input.getBio());
        return speaker;
    }
}