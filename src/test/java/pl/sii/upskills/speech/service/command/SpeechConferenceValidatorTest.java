package pl.sii.upskills.speech.service.command;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import pl.sii.upskills.conference.persistence.Conference;
import pl.sii.upskills.conference.persistence.ConferenceStatus;
import pl.sii.upskills.conference.persistence.TimeSlotVO;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SpeechConferenceValidatorTest {

    private static final LocalDateTime NOW_FOR_TEST =
            LocalDateTime.of(2023, 1, 1, 8, 1);

    private final SpeechConferenceValidator underTest = new SpeechConferenceValidator();

    @Test
    @DisplayName("Should validate speech and conference")
    void happyPath() {
        //given
        Conference conference = conferenceMaker();
        UUID conferenceId = UUID.fromString("0963c134-0141-415f-aaf6-89a502fb58bf");

        //when
        ThrowableAssert.ThrowingCallable lambdaUnderTest = () -> underTest.validate(conference, conferenceId);

        //then
        assertThatNoException().isThrownBy(lambdaUnderTest);
    }

    @Test
    @DisplayName("Should throw speech/conference relation exceptio")
    void noRelation() {
        //given
        Conference conference = conferenceMaker();
        UUID otherConference = UUID.fromString("0163c134-0141-415f-aaf6-89a502fb58bf");

        //when
        Executable lambdaUnderTest = () -> underTest.validate(conference, otherConference);

        //then
        SpeechRelationException exception = assertThrows(SpeechRelationException.class,
                lambdaUnderTest);
        assertThat(exception.getMessage()).isEqualTo("The speech is not related to the conference");
    }

    private Conference conferenceMaker() {
        return new Conference(UUID.fromString("0963c134-0141-415f-aaf6-89a502fb58bf"), "Name", "Title",
                100, ConferenceStatus.DRAFT, null, new TimeSlotVO(NOW_FOR_TEST.plusDays(20),
                NOW_FOR_TEST.plusDays(40)));
    }

}