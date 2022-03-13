package pl.sii.upskills.speech.service.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sii.upskills.conference.persistence.MoneyVO;
import pl.sii.upskills.conference.persistence.TimeSlotVO;
import pl.sii.upskills.conference.service.command.ConferenceCommandService;
import pl.sii.upskills.conference.service.model.ConferenceInput;
import pl.sii.upskills.conference.service.model.ConferenceOutput;
import pl.sii.upskills.speaker.persistence.Speaker;
import pl.sii.upskills.speaker.persistence.SpeakerRepository;
import pl.sii.upskills.speaker.persistence.SpeakerStatus;
import pl.sii.upskills.speech.service.model.SpeechInput;
import pl.sii.upskills.speech.service.model.SpeechOutput;
import pl.sii.upskills.speech.service.model.SpeechSpeakersInput;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SpeechCommandServiceITTest {

    @Autowired
    SpeakerRepository speakerRepository;
    @Autowired
    ConferenceCommandService conferenceCommandService;
    @Autowired
    SpeechCommandService underTest;

    private static final LocalDateTime NOW_FOR_TEST =
            LocalDateTime.of(2024, 1, 1, 0, 1);
    private static final TimeSlotVO CORRECT_TIMESLOT
            = new TimeSlotVO(NOW_FOR_TEST.plusDays(1).plusSeconds(1), NOW_FOR_TEST.plusDays(1).plusHours(2));
    private static final TimeSlotVO CONFERENCE_TIMESLOT
            = new TimeSlotVO(NOW_FOR_TEST.plusDays(1), NOW_FOR_TEST.plusDays(2));

    @Test
    @DisplayName("Should add speech to database")
    void happyPath() {
        // given
        UUID conferenceId = createConference().getId();
        SpeechInput speechInput = new SpeechInput("Holy Hand Grenade", CORRECT_TIMESLOT);

        // when
        SpeechOutput speechOutput = underTest.createSpeech(conferenceId, speechInput);

        // then
        assertThat(speechOutput.getId()).isNotNull();
        assertThat(speechOutput.getTitle()).isEqualTo("Holy Hand Grenade");
    }

    @Test
    @DisplayName("Should add speakers to speech")
    void addSpeakers() {
        // given
        createSpeakers();
        ConferenceOutput conferenceOutput = createConference();
        SpeechInput speechInput = new SpeechInput("Concrete Donkey", CORRECT_TIMESLOT);
        SpeechOutput speechOutput = underTest.createSpeech(conferenceOutput.getId(), speechInput);
        SpeechSpeakersInput speechSpeakersInput = new SpeechSpeakersInput(Set.of(1L, 2L));

        // when
        SpeechOutput result = underTest.addSpeakers(conferenceOutput.getId(), speechOutput.getId(),
                speechSpeakersInput);

        // then
        assertThat(result.getSpeakers()).hasSize(2);
        assertThat(result.getTitle()).isEqualTo("Concrete Donkey");
    }

    private ConferenceOutput createConference() {
        return conferenceCommandService.createConference(new ConferenceInput("Worms", "Armageddon ?",
                200, new MoneyVO(BigDecimal.valueOf(9.00), Currency.getInstance("USD")),
                CONFERENCE_TIMESLOT));
    }

    private void createSpeakers() {
        speakerRepository.save(new Speaker(1L, "Old", "Woman", "12345679",
                "old@grandma.com", "My bio", SpeakerStatus.ACTIVE));
        speakerRepository.save(new Speaker(2L, "Young", "Woman", "12345679",
                "young@grandma.com", "My bio", SpeakerStatus.ACTIVE));
    }
}
