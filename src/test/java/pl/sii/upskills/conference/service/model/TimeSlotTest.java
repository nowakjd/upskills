package pl.sii.upskills.conference.service.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.sii.upskills.conference.persistence.TimeSlotVO;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


class TimeSlotTest {

    private static final LocalDateTime BASE_DATE_TIME = LocalDateTime.of(2022, 4, 22, 8,
            5);

    @Test
    @DisplayName("Timeslots aren't colliding")
    void happyPath() {
        //given
        TimeSlot underTest = createTestTimeslot();
        TimeSlot nonColidingTimeslot = createNonCollidingTimeslot();

        //when
        boolean result =  underTest.doesntCollide(nonColidingTimeslot);

        //then
        assertThat(result).isTrue();

    }

    @Test
    @DisplayName("Timeslot that is later collides with tested one")
    void collideAfter() {
        //given
        TimeSlot underTest = createTestTimeslot();
        TimeSlot laterColidingTimeslot = createCollidingTimeslot();

        //when
        boolean result =  underTest.doesntCollide(laterColidingTimeslot);

        //then
        assertThat(result).isFalse();

    }

    @Test
    @DisplayName("Timeslot that is earlier collides with tested one")
    void collideEarlier() {
        //given
        TimeSlot underTest = createTestTimeslot();
        TimeSlot earlierColidingTimeslot = createEarlierCollidingTimeslot();

        //when
        boolean result =  underTest.doesntCollide(earlierColidingTimeslot);

        //then
        assertThat(result).isFalse();

    }

    @Test
    @DisplayName("Timeslot that starts the same collides with tested one")
    void collideStartDateEqual() {
        //given
        TimeSlot underTest = createTestTimeslot();
        TimeSlot sameStartDate = createSameStartDateTimeslot();

        //when
        boolean result =  underTest.doesntCollide(sameStartDate);

        //then
        assertThat(result).isFalse();

    }

    @Test
    @DisplayName("Timeslot that ends the same collides with tested one")
    void collideEndDateEqual() {
        //given
        TimeSlot underTest = createTestTimeslot();
        TimeSlot sameEndDate = createSameEndDateTimeslot();

        //when
        boolean result =  underTest.doesntCollide(sameEndDate);

        //then
        assertThat(result).isFalse();

    }

    @Test
    @DisplayName("Timeslot that start and ends the same collides with tested one")
    void collideEqual() {
        //given
        TimeSlot underTest = createTestTimeslot();
        TimeSlot equal = createTestTimeslot();

        //when
        boolean result =  underTest.doesntCollide(equal);

        //then
        assertThat(result).isFalse();

    }

    @Test
    @DisplayName("Timeslot starts when tested one ends")
    void endsWhenSecondStarts() {
        //given
        TimeSlot underTest = createTestTimeslot();
        TimeSlot startingOne = createStartDateEqualToEndDate();

        //when
        boolean result =  underTest.doesntCollide(startingOne);

        //then
        assertThat(result).isFalse();

    }

    @Test
    @DisplayName("Timeslot endss when tested one starts")
    void startsWhenSecondEnds() {
        //given
        TimeSlot underTest = createTestTimeslot();
        TimeSlot endingOne = createEndDateEqualToStartDate();

        //when
        boolean result =  underTest.doesntCollide(endingOne);

        //then
        assertThat(result).isFalse();

    }

    private TimeSlotVO createTestTimeslot() {
        return new TimeSlotVO(BASE_DATE_TIME, BASE_DATE_TIME.plusHours(2));
    }

    private TimeSlotVO createCollidingTimeslot() {
        return new TimeSlotVO(BASE_DATE_TIME.plusHours(1), BASE_DATE_TIME.plusHours(3));
    }

    private TimeSlotVO createEarlierCollidingTimeslot() {
        return new TimeSlotVO(BASE_DATE_TIME.minusHours(1), BASE_DATE_TIME.plusHours(1));
    }

    private TimeSlotVO createNonCollidingTimeslot() {
        return new TimeSlotVO(BASE_DATE_TIME.plusHours(4), BASE_DATE_TIME.plusHours(6));
    }

    private TimeSlotVO createSameStartDateTimeslot() {
        return new TimeSlotVO(BASE_DATE_TIME, BASE_DATE_TIME.plusHours(1));
    }

    private TimeSlotVO createSameEndDateTimeslot() {
        return new TimeSlotVO(BASE_DATE_TIME.plusHours(1), BASE_DATE_TIME.plusHours(2));
    }

    private TimeSlotVO createEndDateEqualToStartDate() {
        return new TimeSlotVO(BASE_DATE_TIME.minusHours(2), BASE_DATE_TIME);
    }

    private TimeSlotVO createStartDateEqualToEndDate() {
        return new TimeSlotVO(BASE_DATE_TIME.plusHours(2), BASE_DATE_TIME.plusHours(4));
    }

}