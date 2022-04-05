package pl.sii.upskills.conference.service.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import pl.sii.upskills.broker.ConferenceBroker;
import pl.sii.upskills.broker.SQSAdapter;
import pl.sii.upskills.conference.persistence.*;
import pl.sii.upskills.conference.service.mapper.ConferenceMapper;
import pl.sii.upskills.conference.service.mapper.ConferenceOutputMapper;
import pl.sii.upskills.conference.service.model.ConferenceInput;
import pl.sii.upskills.conference.service.model.ConferenceOutput;
import pl.sii.upskills.speaker.persistence.Speaker;
import pl.sii.upskills.speaker.persistence.SpeakerStatus;
import pl.sii.upskills.speaker.service.mapper.SpeakerOutputMapper;
import pl.sii.upskills.speech.persistence.Speech;
import pl.sii.upskills.speech.service.mapper.SpeechOutputMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ConferenceCommandServiceTest {
    private static final UUID ID_OUTSIDE_DATABASE =
            UUID.fromString("0063c134-0141-415f-aaf6-89a502fb58bf");
    private static final UUID ID_OF_DRAFT_IN_DATABASE =
            UUID.fromString("0963c134-0141-415f-aaf6-89a502fb58bf");
    private static final UUID ID_OF_PUBLISHED_IN_DATABASE =
            UUID.fromString("0163c134-0141-415f-aaf6-89a502fb58bf");
    private static final LocalDateTime NOW_FOR_TEST =
            LocalDateTime.of(2020, 1, 1, 0, 1);
    private static final TimeSlotVO CORRECT_TIMESLOT
            = new TimeSlotVO(NOW_FOR_TEST.plusDays(1), NOW_FOR_TEST.plusDays(2));
    private ConferenceCommandService underTest;
    private ConferenceRepository repository;
    private ConferenceBroker broker;

    @BeforeEach
    void setUp() {
        broker = mock(SQSAdapter.class);
        when(broker.send(any())).thenAnswer(a -> a.getArgument(0));
        repository = mock(ConferenceRepository.class);
        when(repository.save(any())).thenAnswer(a -> a.getArgument(0));
        when(repository.findById(ID_OUTSIDE_DATABASE)).thenReturn(Optional.empty());
        when(repository.findById(ID_OF_DRAFT_IN_DATABASE)).thenReturn(Optional.of(
                new Conference(ID_OF_DRAFT_IN_DATABASE, "name", "title", 100,
                        ConferenceStatus.DRAFT, null, CORRECT_TIMESLOT)));
        when(repository.findById(ID_OF_PUBLISHED_IN_DATABASE)).thenReturn(Optional.of(
                new Conference(ID_OF_PUBLISHED_IN_DATABASE, "name", "title", 100,
                        ConferenceStatus.PUBLISHED, null, CORRECT_TIMESLOT)));
        underTest = new ConferenceCommandService(
                new ConferenceMapper(),
                new ConferenceOutputMapper(new SpeechOutputMapper(new SpeakerOutputMapper())),
                repository,
                new ConferenceValidator(() -> NOW_FOR_TEST),
                broker);
    }

    @Test
    @DisplayName("Should add conference")
    void addConference() {
        //given
        TimeSlotVO timeSlot = new TimeSlotVO(NOW_FOR_TEST.plusDays(100), NOW_FOR_TEST.plusDays(102));
        ConferenceInput conferenceInput = new ConferenceInput("Conference",
                "Conference edition 2020", 100, null,
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

    @Test
    @DisplayName("Should update conference")
    void updateConference() {
        // given
        ConferenceInput conferenceInput = new ConferenceInput("title", "name", 100, null,
                CORRECT_TIMESLOT);
        //when
        ConferenceOutput result = underTest.updateConference(ID_OF_DRAFT_IN_DATABASE, conferenceInput);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(ID_OF_DRAFT_IN_DATABASE);
        assertThat(result.getName()).isEqualTo(conferenceInput.getName());
        assertThat(result.getTitle()).isEqualTo(conferenceInput.getTitle());
        assertThat(result.getNumberOfPlaces()).isEqualTo(conferenceInput.getNumberOfPlaces());
        assertThat(result.getTimeSlot()).isEqualTo(conferenceInput.getTimeSlot());
    }

    @DisplayName("Should throw exception when updated conference is not found in database")
    @Test
    void updatingNonExisting() {
        //given
        ConferenceInput conferenceInput = new ConferenceInput("title", "name", 100, null,
                CORRECT_TIMESLOT);

        //when
        Executable lambdaUnderTest = () -> underTest.updateConference(ID_OUTSIDE_DATABASE, conferenceInput);

        //then
        assertThrows(ConferenceNotFoundException.class, lambdaUnderTest);
    }

    @DisplayName("Should throw exception when updated conference is published")
    @Test
    void updatingPublished() {
        //given
        ConferenceInput conferenceInput = new ConferenceInput("title", "name", 100, null,
                CORRECT_TIMESLOT);
        //when
        Executable lambdaUnderTest = () -> underTest.updateConference(ID_OF_PUBLISHED_IN_DATABASE, conferenceInput);

        //then
        assertThrows(ConferenceNotFoundException.class, lambdaUnderTest);
    }

    @DisplayName("Should throw exception when broker failed to send published conference")
    @Test
    void brokerError() {
        //given
        Conference conference = getPublishableConference();
        when(repository.findById(conference.getId())).thenReturn(Optional.of(conference));
        when(broker.send(any())).thenThrow(new ConferencePublishingException("message"));

        //when
        Executable lambdaUnderTest = () -> underTest.changeStatus(conference.getId(), ConferenceStatus.PUBLISHED);

        //then
        assertThrows(ConferencePublishingException.class, lambdaUnderTest);
    }

    private Conference getPublishableConference() {
        UUID id = UUID.randomUUID();
        String name = "name";
        String title = "title";
        int numberOfPlaces = 1;
        MoneyVO price = new MoneyVO(BigDecimal.valueOf(9), Currency.getInstance("PLN"));
        ConferenceStatus status = ConferenceStatus.DRAFT;
        TimeSlotVO timeSlot = new TimeSlotVO(NOW_FOR_TEST.plusDays(2L), NOW_FOR_TEST.plusDays(4));
        Conference conference = new Conference(id, name, title, numberOfPlaces, status, price, timeSlot);
        Speaker speaker = new Speaker(1L, "name", "last name", "number",
                "email", "bio", SpeakerStatus.ACTIVE);
        Speech speech = new Speech(1L, "title", new TimeSlotVO(NOW_FOR_TEST.plusDays(3),
                NOW_FOR_TEST.plusDays(3).plusMinutes(45)), conference, Set.of(speaker));
        conference.addSpeech(speech);
        return conference;

    }
}
