package pl.sii.upskills.speech.service.command;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import pl.sii.upskills.conference.persistence.Conference;
import pl.sii.upskills.conference.persistence.ConferenceStatus;
import pl.sii.upskills.conference.persistence.TimeSlotVO;
import pl.sii.upskills.speaker.persistence.Speaker;
import pl.sii.upskills.speaker.persistence.SpeakerStatus;
import pl.sii.upskills.speech.persistence.Speech;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SpeakerSetValidatorTest {

    private static final LocalDateTime NOW_FOR_TEST =
            LocalDateTime.of(2023, 1, 1, 8, 1);

    private final SpeakerSetValidator underTest = new SpeakerSetValidator();

    @Test
    @DisplayName("Should validate speaker")
    void happyPath() {
        //given
        Conference conference = conferenceMaker();
        Set<Speaker> speakers = new HashSet<>();
        Speaker speaker = activeSpeakerNoOne();
        Speaker secondSpeaker = activeSpeakerNoTwo();
        speakers.add(speaker);
        speakers.add(secondSpeaker);
        Speech toValidate = new Speech(1L, "Speech title",
                new TimeSlotVO(NOW_FOR_TEST.plusDays(20).plusHours(1), NOW_FOR_TEST.plusDays(20).plusHours(2)),
                conference, new HashSet<>());
        //when
        ThrowableAssert.ThrowingCallable lambdaUnderTest = () -> underTest.validate(speakers, toValidate);
        //then
        assertThatNoException().isThrownBy(lambdaUnderTest);
    }

    @Test
    @DisplayName("Should throw exception when speaker is inactive")
    void inactive() {
        Conference conference = conferenceMaker();
        Set<Speaker> speakers = new HashSet<>();
        speakers.add(inactiveSpeaker());
        Speech toValidate = new Speech(1L, "Speech title",
                new TimeSlotVO(NOW_FOR_TEST.plusDays(20).plusHours(1), NOW_FOR_TEST.plusDays(20).plusHours(2)),
                conference, new HashSet<>());
        //when
        Executable lambdaUnderTest = () -> underTest.validate(speakers, toValidate);
        //then
        SpeakerSetValidationException exception = assertThrows(SpeakerSetValidationException.class, lambdaUnderTest);
        assertThat(exception.getErrors())
                .hasSize(1)
                .allMatch(s -> s.equals("Speaker is inactive"));
    }

    @Test
    @DisplayName("Should throw exception when speaker is inactive without overlapping information")
    void inactiveAndOverlaps() {
        Conference conference = conferenceMaker();
        Set<Speaker> speakers = new HashSet<>();
        speakers.add(inactiveSpeaker());
        conference.addSpeech(new Speech(1L, "Speech title",
                new TimeSlotVO(NOW_FOR_TEST.plusDays(20).plusHours(1), NOW_FOR_TEST.plusDays(20).plusHours(2)),
                conference, speakers));
        Speech toValidate = new Speech(2L, "Speech title",
                new TimeSlotVO(NOW_FOR_TEST.plusDays(20).plusHours(1), NOW_FOR_TEST.plusDays(20).plusHours(2)),
                conference, new HashSet<>());
        //when
        Executable lambdaUnderTest = () -> underTest.validate(speakers, toValidate);
        //then
        SpeakerSetValidationException exception = assertThrows(SpeakerSetValidationException.class, lambdaUnderTest);
        assertThat(exception.getErrors())
                .hasSize(1)
                .allMatch(s -> s.equals("Speaker is inactive"));
    }

    @Test
    @DisplayName("Should throw two exceptions with two different messages")
    void oneSpeakerInactiveSecondOverlaps() {
        Conference conference = conferenceMaker();
        Set<Speaker> speakers = new HashSet<>();
        speakers.add(inactiveSpeaker());
        speakers.add(activeSpeakerNoOne());
        conference.addSpeech(new Speech(1L, "Speech title",
                new TimeSlotVO(NOW_FOR_TEST.plusDays(20).plusHours(1), NOW_FOR_TEST.plusDays(20).plusHours(2)),
                conference, speakers));
        Speech toValidate = new Speech(2L, "Speech title",
                new TimeSlotVO(NOW_FOR_TEST.plusDays(20).plusHours(1), NOW_FOR_TEST.plusDays(20).plusHours(2)),
                conference, new HashSet<>());
        //when
        Executable lambdaUnderTest = () -> underTest.validate(speakers, toValidate);
        //then
        SpeakerSetValidationException exception = assertThrows(SpeakerSetValidationException.class, lambdaUnderTest);
        assertThat(exception.getErrors())
                .hasSize(2)
                .anyMatch(s -> s.equals("Speaker is inactive"))
                .anyMatch(s -> s.equals("Speaker with id 2 is already assigned to speech with id 1"));
    }

    @Test
    @DisplayName("Should throw exception with 3 messages")
    void oneSpeakerInactiveTwoOverlaps() {
        Conference conference = conferenceMaker();
        Set<Speaker> speakers = new HashSet<>();
        Speaker inactiveSpeaker = inactiveSpeaker();
        Speaker activeNoOne = activeSpeakerNoOne();
        speakers.add(inactiveSpeaker);
        speakers.add(activeNoOne);
        conference.addSpeech(new Speech(1L, "Speech title",
                new TimeSlotVO(NOW_FOR_TEST.plusDays(20).plusHours(1), NOW_FOR_TEST.plusDays(20).plusHours(2)),
                conference, speakers));
        Set<Speaker> otherSpeakers = new HashSet<>();
        Speaker activeNoTwo = activeSpeakerNoTwo();
        otherSpeakers.add(activeNoTwo);
        conference.addSpeech(new Speech(2L, "Speech title",
                new TimeSlotVO(NOW_FOR_TEST.plusDays(20).plusHours(1), NOW_FOR_TEST.plusDays(20).plusHours(2)),
                conference, otherSpeakers));
        Set<Speaker> validateSpeakers = new HashSet<>();
        validateSpeakers.add(inactiveSpeaker);
        validateSpeakers.add(activeNoOne);
        validateSpeakers.add(activeNoTwo);
        Speech toValidate = new Speech(3L, "Speech title",
                new TimeSlotVO(NOW_FOR_TEST.plusDays(20).plusHours(1), NOW_FOR_TEST.plusDays(20).plusHours(2)),
                conference, new HashSet<>());
        conference.addSpeech(toValidate);

        //when
        Executable lambdaUnderTest = () -> underTest.validate(validateSpeakers, toValidate);
        //then
        SpeakerSetValidationException exception = assertThrows(SpeakerSetValidationException.class, lambdaUnderTest);
        assertThat(exception.getErrors())
                .hasSize(3)
                .anyMatch(s -> s.equals("Speaker is inactive"))
                .anyMatch(s -> s.equals("Speaker with id 2 is already assigned to speech with id 1"))
                .anyMatch(s -> s.equals("Speaker with id 3 is already assigned to speech with id 2"));
    }

    private Conference conferenceMaker() {
        return new Conference(UUID.fromString("0163c134-0141-415f-aaf6-89a502fb58bf"), "Name", "Title",
                100, ConferenceStatus.DRAFT, null, new TimeSlotVO(NOW_FOR_TEST.plusDays(20),
                NOW_FOR_TEST.plusDays(40)));
    }

    private Speaker inactiveSpeaker() {
        return new Speaker(1L, "First", "Last", "123456789",
                "email@gmail.com", "bio", SpeakerStatus.INACTIVE);
    }

    private Speaker activeSpeakerNoOne() {
        return new Speaker(2L, "First", "Last", "123456789",
                "email@gmail.com", "bio", SpeakerStatus.ACTIVE);
    }

    private Speaker activeSpeakerNoTwo() {
        return new Speaker(3L, "First", "Last", "123456789",
                "email@il.com", "bio", SpeakerStatus.ACTIVE);
    }

}