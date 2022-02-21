package pl.sii.upskills.conference.service.command;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import pl.sii.upskills.conference.persistence.TimeSlotVO;
import pl.sii.upskills.conference.service.model.ConferenceInput;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConferenceInputValidatorTest {

    private static final LocalDateTime NOW_FOR_TEST =
            LocalDateTime.of(2020, 1, 1, 0, 1);
    ConferenceInputValidator underTest;

    @BeforeEach
    void setup() {
        underTest = new ConferenceInputValidator(() -> NOW_FOR_TEST);
    }

    @DisplayName("Should pass validation")
    @Test
    void happyPath() {
        //given
        ConferenceInput conferenceInput = new ConferenceInput("Conference",
                "Conference edition 2020", 100, null,
                new TimeSlotVO(NOW_FOR_TEST.plusDays(100), NOW_FOR_TEST.plusDays(102)));
        //when
        boolean result = underTest.validate(conferenceInput);
        //then
        assertTrue(result);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {" ", "    ", "\t"})
    @DisplayName("Should throw exception when name is not given")
    void withOutName(String name) {
        //given
        ConferenceInput conferenceInput = new ConferenceInput(name,
                "Conference edition 2020", 100, null,
                new TimeSlotVO(NOW_FOR_TEST.plusDays(100), NOW_FOR_TEST.plusDays(102)));
        //when
        Executable lambaUndertest = () -> underTest.validate(conferenceInput);

        //then
        ConferenceValidationException exception = assertThrows(ConferenceValidationException.class, lambaUndertest);
        assertThat(exception.getErrors())
                .hasSize(1)
                .allMatch(s -> s.equals("Name is required"));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {" ", "    ", "\t"})
    void withOutTitle(String title) {
        //given
        ConferenceInput conferenceInput = new ConferenceInput("Conference",
                title, 100, null,
                new TimeSlotVO(NOW_FOR_TEST.plusDays(100), NOW_FOR_TEST.plusDays(102)));
        //when
        Executable lambaUndertest = () -> underTest.validate(conferenceInput);

        //then
        ConferenceValidationException exception = assertThrows(ConferenceValidationException.class, lambaUndertest);
        assertThat(exception.getErrors())
                .hasSize(1)
                .allMatch(s -> s.equals("Title is required"));
    }

    @DisplayName("Should throw exception when number of is smaller than 1")
    @Test
    void zeroPlaces() {
        //given
        ConferenceInput conferenceInput = new ConferenceInput("Conference",
                "Conference 2021", 0, null,
                new TimeSlotVO(NOW_FOR_TEST.plusDays(100), NOW_FOR_TEST.plusDays(102)));
        //when
        Executable lambaUndertest = () -> underTest.validate(conferenceInput);

        //then
        ConferenceValidationException exception = assertThrows(ConferenceValidationException.class, lambaUndertest);
        assertThat(exception.getErrors())
                .hasSize(1)
                .allMatch(s -> s.equals("Number of places must be positive"));
    }

    @DisplayName("Should throw exception when start and end date are not given")
    @Test
    void noTimeSlot() {
        //given
        ConferenceInput conferenceInput = new ConferenceInput("Conference",
                "Conference 2021", 100, null, null);
        //when
        Executable lambaUndertest = () -> underTest.validate(conferenceInput);

        //then
        ConferenceValidationException exception = assertThrows(ConferenceValidationException.class, lambaUndertest);
        assertThat(exception.getErrors())
                .hasSize(1)
                .allMatch(s -> s.equals("Start date and end date are required"));
    }

    @DisplayName("Should throw exception when start date is not given")
    @Test
    void noStartDate() {
        //given
        ConferenceInput conferenceInput = new ConferenceInput("Konferencja",
                "Konferencja 2021", 1, null,
                new TimeSlotVO(null, NOW_FOR_TEST.plusDays(102)));
        //when
        Executable lambaUndertest = () -> underTest.validate(conferenceInput);

        //then
        ConferenceValidationException exception = assertThrows(ConferenceValidationException.class, lambaUndertest);
        assertThat(exception.getErrors())
                .hasSize(1)
                .allMatch(s -> s.equals("Start date is required"));
    }

    @DisplayName("Should throw when end date is not given")
    @Test
    void noEndDate() {
        //given
        ConferenceInput conferenceInput = new ConferenceInput("Conference",
                "Conference 2021", 1, null,
                new TimeSlotVO(NOW_FOR_TEST.plusDays(100), null));
        //when
        Executable lambaUndertest = () -> underTest.validate(conferenceInput);

        //then
        ConferenceValidationException exception = assertThrows(ConferenceValidationException.class, lambaUndertest);
        assertThat(exception.getErrors())
                .hasSize(1)
                .allMatch(s -> s.equals("End date is required"));
    }

    @DisplayName("Should throw exception when start date is in the past")
    @Test
    void startDateInThePast() {
        //given
        ConferenceInput conferenceInput = new ConferenceInput("Conference",
                "Conference 2021", 100, null,
                new TimeSlotVO(NOW_FOR_TEST.minusDays(1), NOW_FOR_TEST.plusDays(102)));
        //when
        Executable lambaUndertest = () -> underTest.validate(conferenceInput);

        //then
        ConferenceValidationException exception = assertThrows(ConferenceValidationException.class, lambaUndertest);
        assertThat(exception.getErrors())
                .hasSize(1)
                .allMatch(s -> s.equals("Start date must be in the future"));
    }

    @DisplayName("Should throw exception when end date is before start date")
    @Test
    void endDateBeforeStartDate() {
        //given
        ConferenceInput conferenceInput = new ConferenceInput("Conference",
                "Conference 2021", 100, null,
                new TimeSlotVO(NOW_FOR_TEST.plusDays(100), NOW_FOR_TEST.plusDays(99)));
        //when
        Executable lambaUndertest = () -> underTest.validate(conferenceInput);

        //then
        ConferenceValidationException exception = assertThrows(ConferenceValidationException.class, lambaUndertest);
        assertThat(exception.getErrors())
                .hasSize(1)
                .allMatch(s -> s.equals("The end date cannot be faster than start date"));
    }
}