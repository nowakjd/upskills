package pl.sii.upskills.conference.service.query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import pl.sii.upskills.conference.persistence.Conference;
import pl.sii.upskills.conference.persistence.ConferenceRepository;
import pl.sii.upskills.conference.persistence.ConferenceStatus;
import pl.sii.upskills.conference.persistence.TimeSlotVO;
import pl.sii.upskills.conference.service.command.ConferenceNotFoundException;
import pl.sii.upskills.conference.service.mapper.ConferenceOutputMapper;
import pl.sii.upskills.conference.service.model.ConferenceOutput;
import pl.sii.upskills.speaker.persistence.Speaker;
import pl.sii.upskills.speaker.persistence.SpeakerStatus;
import pl.sii.upskills.speaker.service.mapper.SpeakerOutputMapper;
import pl.sii.upskills.speech.persistence.Speech;
import pl.sii.upskills.speech.service.mapper.SpeechOutputMapper;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class ConferenceDetailsTest {

    private static final LocalDateTime NOW_FOR_TEST =
            LocalDateTime.of(2020, 1, 1, 0, 1);
    private static final TimeSlotVO CORRECT_TIMESLOT
            = new TimeSlotVO(NOW_FOR_TEST.plusDays(1).plusSeconds(1), NOW_FOR_TEST.plusDays(1).plusHours(2));
    private static final TimeSlotVO CONFERENCE_TIMESLOT
            = new TimeSlotVO(NOW_FOR_TEST.plusDays(1), NOW_FOR_TEST.plusDays(2));
    private static final UUID ID_OF_DRAFT_IN_DATABASE =
            UUID.fromString("0163c134-0141-415f-aaf6-89a502fb58bf");
    private static final UUID ID_OUTSIDE_DATABASE =
            UUID.fromString("0063c134-0141-415f-aaf6-89a502fb58bf");
    private ConferenceQueryService underTest;
    private ConferenceRepository repository;

    @BeforeEach
    void setup() {
        repository = mock(ConferenceRepository.class);
        Function<Conference, ConferenceOutput> mapper = new ConferenceOutputMapper(
                new SpeechOutputMapper(new SpeakerOutputMapper()));
        underTest = new ConferenceQueryService(mapper, repository);
    }

    @Test
    @DisplayName("Should return conference details from repository")
    void conferenceDetails() {
        //given
        Conference conferenceToReturn = getConference();
        when(repository.findById(ID_OF_DRAFT_IN_DATABASE)).thenReturn(Optional.of(conferenceToReturn));

        //when
        ConferenceOutput result = underTest.conferenceDetails(ID_OF_DRAFT_IN_DATABASE);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(ID_OF_DRAFT_IN_DATABASE);
        assertThat(result.getName()).isEqualTo(conferenceToReturn.getName());
        assertThat(result.getTitle()).isEqualTo(conferenceToReturn.getTitle());
        assertThat(result.getNumberOfPlaces()).isEqualTo(conferenceToReturn.getNumberOfPlaces());
        assertThat(result.getTimeSlot()).isEqualTo(conferenceToReturn.getTimeSlot());
        assertThat(result.getSpeeches()).anyMatch(s -> s.getId().equals(getSpeech().getId()));

    }

    @Test
    @DisplayName("Should throw exception when id of conference to display is not in database")
    void notInDatabase() {
        //given
        when(repository.findById(ID_OUTSIDE_DATABASE)).thenReturn(Optional.empty());

        //when
        Executable lambdaUnderTest = () -> underTest.conferenceDetails(ID_OUTSIDE_DATABASE);
        //then
        assertThrows(ConferenceNotFoundException.class, lambdaUnderTest);
    }

    private Conference getConference() {
        Conference toReturn = createConference();
        toReturn.addSpeech(getSpeech());
        return toReturn;
    }

    private Conference createConference() {
        return new Conference(ID_OF_DRAFT_IN_DATABASE, "name", "title", 100,
                ConferenceStatus.DRAFT, null, CONFERENCE_TIMESLOT);

    }

    private Speech getSpeech() {
        return new Speech(2L, "Title", CORRECT_TIMESLOT, createConference(), Set.of(getSpeaker()));
    }

    private Speaker getSpeaker() {
        return new Speaker(1L, "John", "Doe",
                "500400300", "johndoe@sii.pl", " ", SpeakerStatus.ACTIVE);
    }
}

