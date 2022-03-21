package pl.sii.upskills.conference.service.query;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sii.upskills.conference.persistence.MoneyVO;
import pl.sii.upskills.conference.persistence.TimeSlotVO;
import pl.sii.upskills.conference.service.command.ConferenceCommandService;
import pl.sii.upskills.conference.service.command.ConferenceNotFoundException;
import pl.sii.upskills.conference.service.model.ConferenceInput;
import pl.sii.upskills.conference.service.model.ConferenceOutput;
import pl.sii.upskills.speaker.service.command.SpeakerCommandService;
import pl.sii.upskills.speaker.service.model.SpeakerInput;
import pl.sii.upskills.speaker.service.model.SpeakerOutput;
import pl.sii.upskills.speech.service.command.SpeechCommandService;
import pl.sii.upskills.speech.service.model.SpeechInput;
import pl.sii.upskills.speech.service.model.SpeechOutput;
import pl.sii.upskills.speech.service.model.SpeechSpeakersInput;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ConferenceQueryServiceITTest {

    @Autowired
    ConferenceCommandService conferenceCommandService;

    @Autowired
    SpeechCommandService speechCommandService;

    @Autowired
    SpeakerCommandService speakerCommandService;

    @Autowired
    ConferenceQueryService conferenceQueryService;

    private static final LocalDateTime NOW_FOR_TEST =
            LocalDateTime.of(2025, 1, 1, 0, 1);
    private static final TimeSlotVO CONFERENCE_TIMESLOT
            = new TimeSlotVO(NOW_FOR_TEST.plusDays(0), NOW_FOR_TEST.plusDays(2));
    private static final TimeSlotVO SPEECH_TIMESLOT
            = new TimeSlotVO(NOW_FOR_TEST.plusDays(1).plusHours(1), NOW_FOR_TEST.plusDays(1).plusHours(3));
    private static final MoneyVO MONEY = new MoneyVO(BigDecimal.valueOf(10.00), Currency.getInstance("USD"));


    @Test
    @DisplayName("Should return conference details from repository")
    void conferenceDetails() {
        //given
        UUID givenConferenceUUID = getConferenceId();

        //when
        ConferenceOutput conferenceOutput = conferenceQueryService.conferenceDetails(givenConferenceUUID);

        //then
        SpeechOutput speechOutput = conferenceOutput.getSpeeches()
                .stream()
                .filter(x -> Objects.equals(x.getTitle(), "Title speech"))
                .findFirst()
                .orElse(null);

        assertThat(conferenceOutput).isNotNull();
        assertThat(conferenceOutput.getId()).isEqualTo(givenConferenceUUID);
        assertThat(conferenceOutput.getNumberOfPlaces()).isEqualTo(200);
        assertThat(speechOutput).isNotNull();
        assertThat(speechOutput.getTitle()).isEqualTo("Title speech");

        SpeakerOutput speakerOutput = speechOutput.getSpeakers()
                .stream()
                .filter(s -> Objects.equals(s.getFirstName(), "Adam"))
                .findFirst()
                .orElse(null);

        assertThat(speechOutput.getTimeSlot().getStartDate().isEqual(SPEECH_TIMESLOT.getStartDate())).isTrue();
        assertThat(speechOutput.getTimeSlot().getEndDate().isEqual(SPEECH_TIMESLOT.getEndDate())).isTrue();
        assertThat(speakerOutput).isNotNull();
        assertThat(speakerOutput.getLastName()).isEqualTo("Nowak");
        assertThat(speakerOutput.getId()).isNotNull();


    }

    @Test
    @DisplayName("Should throw exception when id of conference isn't in database")
    void conferenceNotFound() {
        //given
        UUID givenConferenceUUID = UUID.randomUUID();

        //when
        Executable lambdaUnderTest = () -> conferenceQueryService.conferenceDetails(givenConferenceUUID);

        //then
        assertThrows(ConferenceNotFoundException.class, lambdaUnderTest);
    }

    private UUID getConferenceId() {
        ConferenceInput conferenceInput = getConferenceInput("Conference", "Title conference");
        UUID result = conferenceCommandService.createConference(conferenceInput).getId();
        SpeechInput speechInput = getSpeechInput("Title speech");
        Long speechId = speechCommandService.createSpeech(result, speechInput).getId();
        SpeakerInput speakerInput = getSpeakerInput("Adam", "Nowak");
        Long speakerId = speakerCommandService.addSpeaker(speakerInput).getId();
        speechCommandService.addSpeakers(result, speechId, new SpeechSpeakersInput(Set.of(speakerId)));
        return result;
    }

    private ConferenceInput getConferenceInput(String name, String title) {
        return new ConferenceInput(name, title,
                200, MONEY, CONFERENCE_TIMESLOT);
    }

    private SpeechInput getSpeechInput(String title) {
        return new SpeechInput(title, SPEECH_TIMESLOT);
    }

    private SpeakerInput getSpeakerInput(String firstName, String lastName) {
        return new SpeakerInput(firstName, lastName, "669669669", "jarek@www.com",
                "My bio");
    }
}

