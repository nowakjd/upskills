package pl.sii.upskills.conference.service.command;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import pl.sii.upskills.conference.persistence.Conference;
import pl.sii.upskills.conference.persistence.ConferenceStatus;
import pl.sii.upskills.conference.persistence.MoneyVO;
import pl.sii.upskills.conference.persistence.TimeSlotVO;
import pl.sii.upskills.conference.service.model.TimeSlot;
import pl.sii.upskills.speaker.persistence.Speaker;
import pl.sii.upskills.speech.persistence.Speech;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


class ConferenceValidatorTest {
    private static final LocalDateTime NOW_FOR_TEST =
            LocalDateTime.of(2020, 1, 1, 0, 1);
    ConferenceValidator underTest;

    @BeforeEach
    void setup() {
        underTest = new ConferenceValidator(() -> NOW_FOR_TEST);
    }

    @DisplayName(" Draft should pass validation")
    @Test
    void happyPath() {
        //given
        Conference conference = new ConferenceBuilder().build();
        //when
        Conference result = underTest.apply(conference);
        //then
        assertThat(result).isEqualTo(conference);

    }

    @DisplayName(" Draft without price should pass validation")
    @Test
    void withoutPrice() {
        //given
        Conference conference = new ConferenceBuilder().setPrice(null).build();
        //when
        Conference result = underTest.apply(conference);
        //then
        assertThat(result).isEqualTo(conference);

    }

    @DisplayName("Should throw exception when speech is outside Conference timeframe")
    @Test
    void speechOutside() {
        //given
        Conference conference = new ConferenceBuilder().build();
        Speech speech = new SpeechBuilder(conference).setTimeSlot(getTimeSlotOutside(conference.getTimeSlot())).build();
        conference.addSpeech(speech);
        //when
        Executable lambdaUnderTest = () -> underTest.apply(conference);

        //then
        ConferenceValidationException exception = assertThrows(ConferenceValidationException.class, lambdaUnderTest);
        assertThat(exception.getErrors())
                .anyMatch(s -> s.equals("Speech with id " + speech.getId() + " is beyond conference time slot"));

    }


    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {" ", "    ", "\t"})
    @DisplayName("Should throw exception when name is not given")
    void withOutName(String name) {
        //given
        Conference conference = new ConferenceBuilder().setName(name).build();

        //when
        Executable lambdaUnderTest = () -> underTest.apply(conference);

        //then
        ConferenceValidationException exception = assertThrows(ConferenceValidationException.class, lambdaUnderTest);
        assertThat(exception.getErrors())
                .hasSize(1)
                .anyMatch(s -> s.equals("Name is required"));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {" ", "    ", "\t"})
    void withOutTitle(String title) {
        //given
        Conference conference = new ConferenceBuilder().setTitle(title).build();

        //when
        Executable lambdaUnderTest = () -> underTest.apply(conference);

        //then
        ConferenceValidationException exception = assertThrows(ConferenceValidationException.class, lambdaUnderTest);
        assertThat(exception.getErrors())
                .anyMatch(s -> s.equals("Title is required"));
    }

    @DisplayName("Should throw exception when number of is smaller than 1")
    @Test
    void zeroPlaces() {
        //given
        Conference conference = new ConferenceBuilder().setNumberOfPlaces(0).build();

        //when
        Executable lambdaUnderTest = () -> underTest.apply(conference);

        //then
        ConferenceValidationException exception = assertThrows(ConferenceValidationException.class, lambdaUnderTest);
        assertThat(exception.getErrors())
                .hasSize(1)
                .anyMatch(s -> s.equals("Number of places must be positive"));
    }

    @DisplayName("Should throw exception when start and end date are not given")
    @Test
    void noTimeSlot() {
        //given
        Conference conference = new ConferenceBuilder().setTimeSlot(null).build();

        //when
        Executable lambdaUnderTest = () -> underTest.apply(conference);

        ConferenceValidationException exception = assertThrows(ConferenceValidationException.class, lambdaUnderTest);
        assertThat(exception.getErrors())
                .anyMatch(s -> s.equals("Start date is required"))
                .anyMatch(s -> s.equals("End date is required"));
    }

    @DisplayName("Should throw exception when start date is not given")
    @Test
    void noStartDate() {
        //given
        Conference conference = new ConferenceBuilder().setStartDate(null).build();

        //when
        Executable lambdaUnderTest = () -> underTest.apply(conference);
        //then
        ConferenceValidationException exception = assertThrows(ConferenceValidationException.class, lambdaUnderTest);
        assertThat(exception.getErrors())
                .hasSize(1)
                .anyMatch(s -> s.equals("Start date is required"));
    }

    @DisplayName("Should throw when end date is not given")
    @Test
    void noEndDate() {
        //given
        Conference conference = new ConferenceBuilder().setEndDate(null).build();

        //when
        Executable lambdaUnderTest = () -> underTest.apply(conference);
        //then
        ConferenceValidationException exception = assertThrows(ConferenceValidationException.class, lambdaUnderTest);
        assertThat(exception.getErrors())
                .hasSize(1)
                .anyMatch(s -> s.equals("End date is required"));
    }

    @DisplayName("Should throw exception when start date is in the past")
    @Test
    void startDateInThePast() {
        //given
        Conference conference = new ConferenceBuilder()
                .setTimeSlot(getTimeSlot(NOW_FOR_TEST.minusDays(1), Duration.ofDays(2))).build();

        //when
        Executable lambdaUnderTest = () -> underTest.apply(conference);

        //then
        ConferenceValidationException exception = assertThrows(ConferenceValidationException.class, lambdaUnderTest);
        assertThat(exception.getErrors())
                .hasSize(1)
                .anyMatch(s -> s.equals("Start date must be in the future"));
    }

    @DisplayName("Should throw exception when end date is before start date")
    @Test
    void endDateBeforeStartDate() {
        //given
        Conference conference = new ConferenceBuilder()
                .setTimeSlot(getTimeSlot(NOW_FOR_TEST.plusDays(1), Duration.ofHours(-1))).build();

        //when
        Executable lambdaUnderTest = () -> underTest.apply(conference);
        //then
        ConferenceValidationException exception = assertThrows(ConferenceValidationException.class, lambdaUnderTest);
        assertThat(exception.getErrors())
                .hasSize(1)
                .anyMatch(s -> s.equals("The end date cannot be faster than start date"));
    }

    private TimeSlotVO getTimeSlot(LocalDateTime when, Duration lenght) {
        return new TimeSlotVO(when, when.plus(lenght));
    }

    private TimeSlotVO getTimeSlotOutside(TimeSlot timeSlot) {
        return new TimeSlotVO(timeSlot.getStartDate().minusMinutes(5), timeSlot.getEndDate());
    }

    private class SpeechBuilder {
        Conference conference;
        Long id = 1L;
        String title = "Speech title";
        TimeSlotVO timeSlot;
        Set<Speaker> speakerSet = new HashSet<>();

        public SpeechBuilder(Conference conference) {
            this.conference = conference;
            this.timeSlot =
                    getTimeSlot(conference.getTimeSlot().getStartDate().plusMinutes(5), Duration.ofMinutes(30));
        }

        SpeechBuilder setTimeSlot(TimeSlotVO timeSlot) {
            this.timeSlot = timeSlot;
            return this;
        }

        Speech build() {
            return new Speech(id, title, timeSlot, conference, speakerSet);
        }
    }

    private class ConferenceBuilder {
        UUID id = UUID.randomUUID();
        String name = "name";
        String title = "title";
        Integer numberOfPlaces = 1;
        MoneyVO price = new MoneyVO(BigDecimal.valueOf(9), Currency.getInstance("PLN"));
        ConferenceStatus status = ConferenceStatus.DRAFT;
        TimeSlotVO timeSlot = getTimeSlot(NOW_FOR_TEST.plusDays(2L), Duration.ofDays(2));

        Conference build() {
            return new Conference(id, name, title, numberOfPlaces, status, price, timeSlot);
        }

        ConferenceBuilder setName(String name) {
            this.name = name;
            return this;
        }

        ConferenceBuilder setTimeSlot(TimeSlotVO timeSlot) {
            this.timeSlot = timeSlot;
            return this;
        }

        ConferenceBuilder setNumberOfPlaces(int numberOfPlaces) {
            this.numberOfPlaces = numberOfPlaces;
            return this;
        }

        public ConferenceBuilder setStartDate(LocalDateTime startDate) {
            this.timeSlot = new TimeSlotVO(startDate, this.timeSlot.getEndDate());
            return this;

        }

        public ConferenceBuilder setEndDate(LocalDateTime endDate) {
            this.timeSlot = new TimeSlotVO(this.timeSlot.getStartDate(), endDate);
            return this;
        }

        public ConferenceBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public ConferenceBuilder setPrice(MoneyVO price) {
            this.price = price;
            return this;

        }
    }
}
