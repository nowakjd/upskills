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
import pl.sii.upskills.speech.service.model.SpeechSpeakersInput;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SpeakerValidatorTest {

    private static final LocalDateTime NOW_FOR_TEST =
            LocalDateTime.of(2023, 1, 1, 8, 1);

    @Test
    @DisplayName("Should validate speaker")
    void happyPath() {
        //given
        Conference conference = conferenceMaker();
        Speaker speaker = activeSpeakerNoOne();
        Speaker secondSpeaker = activeSpeakerNoTwo();
        Set<Speaker> speakers = Set.of(speaker, secondSpeaker);
        SpeechSpeakersInput input = new SpeechSpeakersInput(Set.of(2L, 3L));
        Speech toValidate = new Speech(1L, "Speech title",
                new TimeSlotVO(NOW_FOR_TEST.plusDays(20).plusHours(1), NOW_FOR_TEST.plusDays(20).plusHours(2)),
                conference, speakers);
        SpeakerValidator underTest = new SpeakerValidator();
        //when
        ThrowableAssert.ThrowingCallable lambdaUnderTest = (() -> underTest.validateSpeakers(toValidate, input));
        //then
        assertThatNoException().isThrownBy(lambdaUnderTest);
    }

    @Test
    @DisplayName("Should throw exception when speaker is inactive")
    void inactive() {
        Conference conference = conferenceMaker();
        Speaker speaker = inactiveSpeaker();
        Set<Speaker> speakers = Set.of(speaker);
        SpeechSpeakersInput input = new SpeechSpeakersInput(Set.of(1L));
        Speech toValidate = new Speech(1L, "Speech title",
                new TimeSlotVO(NOW_FOR_TEST.plusDays(20).plusHours(1), NOW_FOR_TEST.plusDays(20).plusHours(2)),
                conference, speakers);
        SpeakerValidator underTest = new SpeakerValidator();
        //when
        Executable lambdaUnderTest = (() -> underTest.validateSpeakers(toValidate, input));
        //then
        SpeakerSetValidationException exception = assertThrows(SpeakerSetValidationException.class, lambdaUnderTest);
        assertThat(exception.getErrors())
                .hasSize(1)
                .allMatch(s -> s.equals("Speaker with id " + inactiveSpeaker().getId() + " is inactive"));
    }

    @Test
    @DisplayName("Should throw exception when speaker is inactive without overlapping information")
    void inactiveAndOverlaps() {
        Conference conference = conferenceMaker();
        Speaker speaker = inactiveSpeaker();
        Set<Speaker> speakers = Set.of(speaker);
        SpeechSpeakersInput input = new SpeechSpeakersInput(Set.of(1L));
        conference.addSpeech(new Speech(1L, "Speech title",
                new TimeSlotVO(NOW_FOR_TEST.plusDays(20).plusHours(1), NOW_FOR_TEST.plusDays(20).plusHours(2)),
                conference, speakers));
        Speech toValidate = new Speech(2L, "Speech title",
                new TimeSlotVO(NOW_FOR_TEST.plusDays(20).plusHours(1), NOW_FOR_TEST.plusDays(20).plusHours(2)),
                conference, speakers);
        SpeakerValidator underTest = new SpeakerValidator();
        //when
        Executable lambdaUnderTest = (() -> underTest.validateSpeakers(toValidate, input));
        //then
        SpeakerSetValidationException exception = assertThrows(SpeakerSetValidationException.class, lambdaUnderTest);
        assertThat(exception.getErrors())
                .hasSize(1)
                .allMatch(s -> s.equals("Speaker with id " + inactiveSpeaker().getId() + " is inactive"));
    }

    @Test
    @DisplayName("Should throw two exceptions with two different messages")
    void oneSpeakerInactiveSecondOverlaps() {
        Conference conference = conferenceMaker();
        Speaker inactiveSpeaker = inactiveSpeaker();
        Speaker activeSpeakerNoOne = activeSpeakerNoOne();
        Set<Speaker> speakers = Set.of(inactiveSpeaker, activeSpeakerNoOne);
        SpeechSpeakersInput input = new SpeechSpeakersInput(Set.of(1L, 2L));
        conference.addSpeech(new Speech(1L, "Speech title",
                new TimeSlotVO(NOW_FOR_TEST.plusDays(20).plusHours(1), NOW_FOR_TEST.plusDays(20).plusHours(2)),
                conference, speakers));
        Speech toValidate = new Speech(2L, "Speech title",
                new TimeSlotVO(NOW_FOR_TEST.plusDays(20).plusHours(1), NOW_FOR_TEST.plusDays(20).plusHours(2)),
                conference, speakers);
        SpeakerValidator underTest = new SpeakerValidator();
        //when
        Executable lambdaUnderTest = (() -> underTest.validateSpeakers(toValidate, input));
        //then
        SpeakerSetValidationException exception = assertThrows(SpeakerSetValidationException.class, lambdaUnderTest);
        assertThat(exception.getErrors())
                .hasSize(2)
                .anyMatch(s -> s.equals("Speaker with id " + inactiveSpeaker().getId() + " is inactive"))
                .anyMatch(s -> s.equals("Speaker with id 2 is already assigned to speech with id 1"));
    }

    @Test
    @DisplayName("Should throw exception with 4 messages")
    void oneSpeakerInactiveTwoOverlapsOneNonExisting() {
        Conference conference = conferenceMaker();
        Speaker inactiveSpeaker = inactiveSpeaker();
        Speaker activeNoOne = activeSpeakerNoOne();
        Set<Speaker> speakers = Set.of(inactiveSpeaker, activeNoOne);
        conference.addSpeech(new Speech(1L, "Speech title",
                new TimeSlotVO(NOW_FOR_TEST.plusDays(20).plusHours(1), NOW_FOR_TEST.plusDays(20).plusHours(2)),
                conference, speakers));
        Speaker activeNoTwo = activeSpeakerNoTwo();
        Set<Speaker> otherSpeakers = Set.of(activeNoTwo);
        conference.addSpeech(new Speech(2L, "Speech title",
                new TimeSlotVO(NOW_FOR_TEST.plusDays(20).plusHours(1), NOW_FOR_TEST.plusDays(20).plusHours(2)),
                conference, otherSpeakers));
        Set<Speaker> validateSpeakers = Set.of(inactiveSpeaker, activeNoOne, activeNoTwo);
        SpeechSpeakersInput input = new SpeechSpeakersInput(Set.of(1L, 2L, 3L, 76L));
        Speech toValidate = new Speech(3L, "Speech title",
                new TimeSlotVO(NOW_FOR_TEST.plusDays(20).plusHours(1), NOW_FOR_TEST.plusDays(20).plusHours(2)),
                conference, validateSpeakers);
        conference.addSpeech(toValidate);
        SpeakerValidator underTest = new SpeakerValidator();

        //when
        Executable lambdaUnderTest = (() -> underTest.validateSpeakers(toValidate, input));
        //then
        SpeakerSetValidationException exception = assertThrows(SpeakerSetValidationException.class, lambdaUnderTest);
        assertThat(exception.getErrors())
                .hasSize(4)
                .anyMatch(s -> s.equals("Speaker with id " + inactiveSpeaker.getId() + " is inactive"))
                .anyMatch(s -> s.equals("Speaker with id 2 is already assigned to speech with id 1"))
                .anyMatch(s -> s.equals("Speaker with id 3 is already assigned to speech with id 2"))
                .anyMatch(s -> s.equals("Speaker with id 76 doesn't exist"));
    }

    @Test
    @DisplayName("Should throw exception when input is empty")
    void emptyInput() {
        Conference conference = conferenceMaker();
        SpeechSpeakersInput input = new SpeechSpeakersInput(new HashSet<>());
        Speech toValidate = new Speech(1L, "Speech title",
                new TimeSlotVO(NOW_FOR_TEST.plusDays(20).plusHours(1), NOW_FOR_TEST.plusDays(20).plusHours(2)),
                conference, new HashSet<>());
        SpeakerValidator underTest = new SpeakerValidator();
        //when
        Executable lambdaUnderTest = (() -> underTest.validateSpeakers(toValidate, input));
        //then
        SpeakerSetValidationException exception = assertThrows(SpeakerSetValidationException.class, lambdaUnderTest);
        assertThat(exception.getErrors())
                .hasSize(1)
                .allMatch(s -> s.equals("Please select speakers to add them to speech"));
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