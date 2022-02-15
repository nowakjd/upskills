package pl.sii.upskills.conference.service.command;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.sii.upskills.conference.persistence.TimeSlotVO;
import pl.sii.upskills.conference.service.model.ConferenceInput;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ConferenceInputValidatorTest {

    ConferenceInputValidator underTest;
    private final static LocalDateTime nowForTest =
            LocalDateTime.of(2020,1,1,0,1);

    @BeforeEach
    void setup(){
        underTest = new ConferenceInputValidator(()->nowForTest);
    }

    @DisplayName("Should pass validation")
    @Test
    void happyPath(){
        //given
        ConferenceInput conferenceInput = new ConferenceInput("Konferencja",
                "Konfercja edycja 2020", 100, null,
                new TimeSlotVO(nowForTest.plusDays(100),nowForTest.plusDays(102)));
        //when
        Boolean result = underTest.validate(conferenceInput);
        //then
        assertTrue(result);
    }

}