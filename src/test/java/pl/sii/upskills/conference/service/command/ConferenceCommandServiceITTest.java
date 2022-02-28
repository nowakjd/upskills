package pl.sii.upskills.conference.service.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sii.upskills.conference.persistence.MoneyVO;
import pl.sii.upskills.conference.persistence.TimeSlotVO;
import pl.sii.upskills.conference.service.model.ConferenceInput;
import pl.sii.upskills.conference.service.model.ConferenceOutput;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ConferenceCommandServiceITTest {

    @Autowired
    ConferenceCommandService underTest;

    @Test
    @DisplayName("Should add conference to database")
    void happyPath() {
        // given
        ConferenceInput conferenceInput = new ConferenceInput("First", "Our First Conference",
                200, new MoneyVO(BigDecimal.valueOf(9.00), Currency.getInstance("USD")),
                new TimeSlotVO(LocalDateTime.of(2023, 1, 1, 0, 1),
                LocalDateTime.of(2023, 1, 1, 8, 1)));

        // when
        ConferenceOutput conferenceOutput = underTest.createConference(conferenceInput);

        // then
        assertThat(conferenceOutput.getId()).isNotNull();
        assertThat(conferenceOutput.getNumberOfPlaces()).isEqualTo(200);
    }

}
