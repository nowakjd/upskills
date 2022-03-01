package pl.sii.upskills.speaker.service.command;

public class SpeakerBadRequestException extends RuntimeException {
    public SpeakerBadRequestException(String message) {
        super(message);
    }
}
