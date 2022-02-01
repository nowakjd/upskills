package pl.sii.upskills.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.sii.upskills.speaker.service.SpeakerService;

class SpeakerServiceTest {
    SpeakerService speakerService;


    @BeforeEach
    void setUp() {
       // speakerService = new SpeakerService();
    }

    @Test
    @DisplayName("Should throw exception when first name is not given")
    void shouldThrowExceptionWhenFirstNameIsNotGiven() {
    }
    @Test
    void shouldThrowExceptionWhenLastNameIsNotGiven() {
    }
    @Test
    void shouldThrowExceptionWhenEmailOrPhoneNumberIsNotGiven() {
    }
}