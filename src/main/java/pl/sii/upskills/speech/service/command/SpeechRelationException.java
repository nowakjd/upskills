package pl.sii.upskills.speech.service.command;

public class SpeechRelationException extends RuntimeException {

    public SpeechRelationException() {
        super("Speech relation with this entity doesn't exists");
    }
}
