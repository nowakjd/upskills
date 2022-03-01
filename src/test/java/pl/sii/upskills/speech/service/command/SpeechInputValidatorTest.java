package pl.sii.upskills.speech.service.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import pl.sii.upskills.conference.persistence.Conference;
import pl.sii.upskills.conference.persistence.ConferenceStatus;
import pl.sii.upskills.conference.persistence.TimeSlotVO;
import pl.sii.upskills.speech.service.model.SpeechInput;

import java.time.LocalDateTime;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class SpeechInputValidatorTest {

    private static final LocalDateTime NOW_FOR_TEST =
            LocalDateTime.of(2022, 3, 1, 0, 1);
    private static final TimeSlotVO CORRECT_TIMESLOT
            = new TimeSlotVO(NOW_FOR_TEST.plusDays(1), NOW_FOR_TEST.plusDays(2));
    Conference conference = new Conference(UUID.fromString("0963c134-0141-415f-aaf6-89a502fb58bf"), "name",
            "title", 100, ConferenceStatus.DRAFT, null, CORRECT_TIMESLOT);
    SpeechInputValidator underTest;
    TimeSlotVO speechTimeSlot = new TimeSlotVO(NOW_FOR_TEST.plusDays(1).plusSeconds(1),
            NOW_FOR_TEST.plusDays(1).plusHours(3));

    @BeforeEach
    void setup() {
        underTest = new SpeechInputValidator(() -> NOW_FOR_TEST);
    }

    @DisplayName("Should pass validation")
    @Test
    void happyPath() {
        //given
        SpeechInput speechInput = new SpeechInput("War never changes", speechTimeSlot, new TreeSet<>());
        //when
        boolean result = underTest.validate(speechInput, conference);
        //then
        assertTrue(result);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {" ", "    ", "\t"})
    @DisplayName("Should throw exception when title is not given")
    void withoutTitle(String title) {
        //given
        SpeechInput speechInput = new SpeechInput(title, speechTimeSlot, new TreeSet<>());
        //when
        Executable lambdaUndertest = () -> underTest.validate(speechInput, conference);

        //then
        SpeechValidationException exception = assertThrows(SpeechValidationException.class, lambdaUndertest);
        assertThat(exception.getErrors())
                .hasSize(1)
                .allMatch(s -> s.equals("Title is required"));
    }

    @DisplayName("Should throw exception when start and end date are not given")
    @Test
    void noTimeSlot() {
        //given
        SpeechInput speechInput = new SpeechInput("War never changes", null, new TreeSet<>());
        //when
        Executable lambdaUndertest = () -> underTest.validate(speechInput, conference);

        //then
        SpeechValidationException exception = assertThrows(SpeechValidationException.class, lambdaUndertest);
        assertThat(exception.getErrors())
                .hasSize(1)
                .allMatch(s -> s.equals("Start date and end date are required"));
    }

    @DisplayName("Should throw exception when start date is not given")
    @Test
    void noStartDateInTimeSlot() {
        //given
        SpeechInput speechInput = new SpeechInput("War never changes", new TimeSlotVO(null,
                speechTimeSlot.getEndDate()), new TreeSet<>());
        //when
        Executable lambdaUndertest = () -> underTest.validate(speechInput, conference);

        //then
        SpeechValidationException exception = assertThrows(SpeechValidationException.class, lambdaUndertest);
        assertThat(exception.getErrors())
                .hasSize(1)
                .allMatch(s -> s.equals("Start date is required"));
    }

    @DisplayName("Should throw exception when start date is in past")
    @Test
    void noStartDateInPast() {
        //given
        SpeechInput speechInput = new SpeechInput("War never changes", new TimeSlotVO(NOW_FOR_TEST.minusDays(1),
                NOW_FOR_TEST.minusHours(17)), new TreeSet<>());
        Conference conference = new Conference(UUID.fromString("0963c134-0141-415f-aaf6-89a502fb58bf"), "name",
                "title", 100, ConferenceStatus.DRAFT, null,
                new TimeSlotVO(NOW_FOR_TEST.minusDays(1).minusMinutes(1), NOW_FOR_TEST));
        //when
        Executable lambdaUndertest = () -> underTest.validate(speechInput, conference);

        //then
        SpeechValidationException exception = assertThrows(SpeechValidationException.class, lambdaUndertest);
        assertThat(exception.getErrors())
                .hasSize(1)
                .allMatch(s -> s.equals("Start date must be in the future"));
    }

    @DisplayName("Should throw exception when end date is in before start")
    @Test
    void endsBeforeStart() {
        //given
        SpeechInput speechInput = new SpeechInput("War never changes", new TimeSlotVO(NOW_FOR_TEST,
                NOW_FOR_TEST.minusHours(17)), new TreeSet<>());
        Conference conference = new Conference(UUID.fromString("0963c134-0141-415f-aaf6-89a502fb58bf"), "name",
                "title", 100, ConferenceStatus.DRAFT, null,
                new TimeSlotVO(NOW_FOR_TEST.minusDays(1), NOW_FOR_TEST.plusDays(2)));
        //when
        Executable lambdaUndertest = () -> underTest.validate(speechInput, conference);

        //then
        SpeechValidationException exception = assertThrows(SpeechValidationException.class, lambdaUndertest);
        assertThat(exception.getErrors())
                .anyMatch(s -> s.equals("The end date cannot be faster than start date"));
    }

    @ParameterizedTest
    @MethodSource("speechTmeslotProvider")
    @DisplayName("Should throw exception when duration of speech is invalid")
    void speechDurationInvalid(TimeSlotVO timeSlotVO) {
        //given
        SpeechInput speechInput = new SpeechInput("War never changes", timeSlotVO, new TreeSet<>());
        //when
        Executable lambdaUndertest = () -> underTest.validate(speechInput, conference);

        //then
        SpeechValidationException exception = assertThrows(SpeechValidationException.class, lambdaUndertest);
        assertThat(exception.getErrors())
                .hasSize(1)
                .allMatch(s -> s.equals("Duration of speech must be at least 5 minutes and it"
                        + " cannot be longer than 8 hours"));
    }

    @DisplayName("Should throw exception when speech is out of conference")
    @Test
    void speechOutOfConference() {
        //given
        SpeechInput speechInput = new SpeechInput("War never changes",
                new TimeSlotVO(NOW_FOR_TEST, NOW_FOR_TEST.plusHours(2)), new TreeSet<>());
        //when
        Executable lambdaUndertest = () -> underTest.validate(speechInput, conference);

        //then
        SpeechValidationException exception = assertThrows(SpeechValidationException.class, lambdaUndertest);
        assertThat(exception.getErrors())
                .hasSize(1)
                .allMatch(s -> s.equals("Speech must be in range of conference"));
    }

    private static Stream<Arguments> speechTmeslotProvider() {
        return Stream.of(
                arguments(new TimeSlotVO(NOW_FOR_TEST.plusDays(1).plusSeconds(1),
                        NOW_FOR_TEST.plusDays(1).plusMinutes(3))),
                arguments(new TimeSlotVO(NOW_FOR_TEST.plusDays(1).plusSeconds(1),
                        NOW_FOR_TEST.plusDays(1).plusHours(10)))
        );
    }
}