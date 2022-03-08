package pl.sii.upskills.conference.service.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.sii.upskills.conference.persistence.TimeSlotVO;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


class TimeSlotTest {

    private static final LocalDateTime BASE_START_DATE = LocalDateTime.of(2022, 4, 22, 8,
            5);
    private static final LocalDateTime BASE_END_DATE = LocalDateTime.of(2022, 4, 22, 10,
            5);


    @Test
    @DisplayName("Timeslots aren't colliding")
    void happyPath() {
        //given
        TimeSlot undertest = createTestTimeslot();
        TimeSlot nonColidingTimeslot = createNonCollidingTimeslot();

        //when
        boolean result =  undertest.doesntCollide(nonColidingTimeslot);

        //then
        assertThat(result).isTrue();

    }

    @Test
    @DisplayName("Timeslot that is later collides with tested one")
    void collideAfter() {
        //given
        TimeSlot undertest = createTestTimeslot();
        TimeSlot laterColidingTimeslot = createCollidingTimeslot();

        //when
        boolean result =  undertest.doesntCollide(laterColidingTimeslot);

        //then
        assertThat(result).isFalse();

    }

    @Test
    @DisplayName("Timeslot that is earlier collides with tested one")
    void collideEarlier() {
        //given
        TimeSlot undertest = createTestTimeslot();
        TimeSlot earlierColidingTimeslot = createEarlierCollidingTimeslot();

        //when
        boolean result =  undertest.doesntCollide(earlierColidingTimeslot);

        //then
        assertThat(result).isFalse();

    }

    @Test
    @DisplayName("Timeslot that starts the same collides with tested one")
    void collideStartDateEqual() {
        //given
        TimeSlot undertest = createTestTimeslot();
        TimeSlot sameStartDate = createSameStartDateTimeslot();

        //when
        boolean result =  undertest.doesntCollide(sameStartDate);

        //then
        assertThat(result).isFalse();

    }

    @Test
    @DisplayName("Timeslot that ends the same collides with tested one")
    void collideEndDateEqual() {
        //given
        TimeSlot undertest = createTestTimeslot();
        TimeSlot sameEndDate = createSameEndDateTimeslot();

        //when
        boolean result =  undertest.doesntCollide(sameEndDate);

        //then
        assertThat(result).isFalse();

    }

    @Test
    @DisplayName("Timeslot that start and ends the same collides with tested one")
    void collideEqual() {
        //given
        TimeSlot undertest = createTestTimeslot();
        TimeSlot equal = createTestTimeslot();

        //when
        boolean result =  undertest.doesntCollide(equal);

        //then
        assertThat(result).isFalse();

    }

    @Test
    @DisplayName("Timeslot starts when tested one ends")
    void endsWhenSecondStarts() {
        //given
        TimeSlot undertest = createTestTimeslot();
        TimeSlot startingOne = createStartDateEqualToEndDate();

        //when
        boolean result =  undertest.doesntCollide(startingOne);

        //then
        assertThat(result).isFalse();

    }

    @Test
    @DisplayName("Timeslot endss when tested one starts")
    void startsWhenSecondEnds() {
        //given
        TimeSlot undertest = createTestTimeslot();
        TimeSlot endingOne = createEndDateEqualToStartDate();

        //when
        boolean result =  undertest.doesntCollide(endingOne);

        //then
        assertThat(result).isFalse();

    }

    private TimeSlotVO createTestTimeslot() {
        return new TimeSlotVO(BASE_START_DATE, BASE_END_DATE);
    }

    private TimeSlotVO createCollidingTimeslot() {
        return new TimeSlotVO(BASE_START_DATE.plusHours(1), BASE_END_DATE.plusHours(1));
    }

    private TimeSlotVO createEarlierCollidingTimeslot() {
        return new TimeSlotVO(BASE_START_DATE.minusHours(1), BASE_END_DATE.minusHours(1));
    }

    private TimeSlotVO createNonCollidingTimeslot() {
        return new TimeSlotVO(BASE_START_DATE.plusHours(4), BASE_END_DATE.plusHours(4));
    }

    private TimeSlotVO createSameStartDateTimeslot() {
        return new TimeSlotVO(BASE_START_DATE, BASE_END_DATE.minusHours(1));
    }

    private TimeSlotVO createSameEndDateTimeslot() {
        return new TimeSlotVO(BASE_START_DATE.plusHours(1), BASE_END_DATE);
    }

    private TimeSlotVO createEndDateEqualToStartDate() {
        return new TimeSlotVO(BASE_START_DATE.minusHours(2), BASE_START_DATE);
    }

    private TimeSlotVO createStartDateEqualToEndDate() {
        return new TimeSlotVO(BASE_END_DATE, BASE_END_DATE.plusHours(2));
    }

}