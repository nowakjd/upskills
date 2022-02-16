package pl.sii.upskills.conference.service.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.sii.upskills.conference.persistence.ConferenceRepository;
import pl.sii.upskills.conference.persistence.TimeSlotVO;
import pl.sii.upskills.conference.service.mapper.ConferenceMapper;
import pl.sii.upskills.conference.service.mapper.ConferenceOutputMapper;
import pl.sii.upskills.conference.service.model.ConferenceInput;
import pl.sii.upskills.conference.service.model.ConferenceOutput;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ConferenceCommandServiceTest {
    private static final LocalDateTime nowForTest =
            LocalDateTime.of(2020, 1, 1, 0, 1);
    private ConferenceCommandService underTest;

    @BeforeEach
    void setUp() {
        ConferenceRepository repository = mock(ConferenceRepository.class);
        when(repository.save(any())).thenAnswer(a -> a.getArgument(0));
        underTest = new ConferenceCommandService(
                new ConferenceInputValidator(() -> nowForTest),
                new ConferenceMapper(),
                new ConferenceOutputMapper(),
                repository);
    }

    @Test
    @DisplayName("Should add conference")
    void addConference() {
        //given
        TimeSlotVO timeSlot = new TimeSlotVO(nowForTest.plusDays(100), nowForTest.plusDays(102));
        ConferenceInput conferenceInput = new ConferenceInput("Konferencja",
                "Konferencja edycja 2020", 100, null,
                timeSlot);
        //when
        ConferenceOutput result = underTest.createConference(conferenceInput);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(conferenceInput.getName());
        assertThat(result.getTitle()).isEqualTo(conferenceInput.getTitle());
        assertThat(result.getNumberOfPlaces()).isEqualTo(conferenceInput.getNumberOfPlaces());
        assertThat(result.getTimeSlot()).isEqualTo(conferenceInput.getTimeSlot());
    }

}
