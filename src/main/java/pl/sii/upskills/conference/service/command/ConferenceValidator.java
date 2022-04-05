package pl.sii.upskills.conference.service.command;

import org.springframework.stereotype.Component;
import pl.sii.upskills.common.TimeService;
import pl.sii.upskills.conference.persistence.Conference;

import java.util.function.UnaryOperator;

@Component
public class ConferenceValidator implements UnaryOperator<Conference> {
    private final DraftValidator draftValidation;
    private final PublishedValidator publishedValidator = new PublishedValidator();

    public ConferenceValidator(TimeService timeService) {
        draftValidation = new DraftValidator(timeService);
    }

    @Override
    public Conference apply(Conference conference) {
        ConferenceValidationException exception = new ConferenceValidationException();
        switch (conference.getStatus()) {
            case PUBLISHED -> {
                publishedValidator.validatePublished(conference).ifPresent(exception::addErrors);
                draftValidation.validateDraft(conference).ifPresent(exception::addErrors);
            }
            case DRAFT -> draftValidation.validateDraft(conference).ifPresent(exception::addErrors);
            default -> {
            }
        }
        throwExceptionIfHasErrors(exception);
        return conference;
    }

    private void throwExceptionIfHasErrors(ConferenceValidationException exception) {
        if (exception.hasErrors()) {
            throw exception;
        }
    }


}
