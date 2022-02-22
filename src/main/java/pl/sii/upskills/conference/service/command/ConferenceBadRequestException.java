package pl.sii.upskills.conference.service.command;

public class ConferenceBadRequestException extends RuntimeException {

    public ConferenceBadRequestException(String message) {
        super(message);
    }
}
