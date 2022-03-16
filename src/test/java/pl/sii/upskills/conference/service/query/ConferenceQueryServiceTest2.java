package pl.sii.upskills.conference.service.query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.sii.upskills.conference.persistence.Conference;
import pl.sii.upskills.conference.persistence.ConferenceRepository;
import pl.sii.upskills.conference.persistence.ConferenceStatus;
import pl.sii.upskills.conference.persistence.TimeSlotVO;
import pl.sii.upskills.conference.service.mapper.ConferenceOutputMapper;
import pl.sii.upskills.conference.service.model.ConferenceOutput;
import pl.sii.upskills.speaker.service.mapper.SpeakerOutputMapper;
import pl.sii.upskills.speech.persistence.Speech;
import pl.sii.upskills.speech.persistence.SpeechRepository;
import pl.sii.upskills.speech.service.mapper.SpeechOutputMapper;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ConferenceQueryServiceTest2 {

    private ConferenceQueryService underTest;
    private ConferenceRepository repository;

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


    @BeforeEach
    void setUp() {

    }

    @Test
    @DisplayName("Should return conference details from repository")
    void conferenceDetails() {

        Conference conference1 = new Conference(ID_OF_DRAFT_IN_DATABASE, "name", "title", 100,
                ConferenceStatus.DRAFT, null, CONFERENCE_TIMESLOT);
        Speech speech = new Speech(2L, "Title", CORRECT_TIMESLOT, conference1, new HashSet<>());

        ConferenceRepository repository = mock(ConferenceRepository.class);
        when(repository.findById(ID_OF_DRAFT_IN_DATABASE)).thenReturn(Optional.of(conference1));
        when(repository.findById(ID_OUTSIDE_DATABASE)).thenReturn(Optional.empty());
        SpeechRepository speechRepository = mock(SpeechRepository.class);
        when(speechRepository.save(any())).thenAnswer(a -> a.getArgument(0));
        when(speechRepository.findById(2L)).thenReturn(Optional.of(speech));
        conference1.addSpeech(speech);

        Function<Conference, ConferenceOutput> mapper = new ConferenceOutputMapper(
                new SpeechOutputMapper(new SpeakerOutputMapper()));
        underTest = new ConferenceQueryService(mapper, repository);


        //when
        ConferenceOutput result = underTest.conferenceDetails(ID_OUTSIDE_DATABASE);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(ID_OF_DRAFT_IN_DATABASE);
        assertThat(result.getName()).isEqualTo(conference1.getName());
        assertThat(result.getTitle()).isEqualTo(conference1.getTitle());
        assertThat(result.getNumberOfPlaces()).isEqualTo(conference1.getNumberOfPlaces());
        assertThat(result.getTimeSlot()).isEqualTo(conference1.getTimeSlot());

    }
}

