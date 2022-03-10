package pl.sii.upskills.speech.service.command;

public class SpeechRelationException extends RuntimeException {

    public SpeechRelationException() {
        super("The speech is not related to the conference");
    }

}
