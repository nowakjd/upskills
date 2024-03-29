package pl.sii.upskills.speech.service.command;

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
import pl.sii.upskills.speaker.service.mapper.SpeakerOutputMapper;
import pl.sii.upskills.speaker.service.query.SpeakerQueryService;
import pl.sii.upskills.speech.persistence.Speech;
import pl.sii.upskills.speech.persistence.SpeechRepository;
import pl.sii.upskills.speech.service.mapper.SpeechMapper;
import pl.sii.upskills.speech.service.mapper.SpeechOutputMapper;
import pl.sii.upskills.speech.service.model.SpeechInput;
import pl.sii.upskills.speech.service.model.SpeechOutput;
import pl.sii.upskills.speech.service.model.SpeechSpeakersInput;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.BiFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SpeechCommandServiceTest {

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
    private SpeechCommandService underTest;

    @BeforeEach
    void setUp() {
        Conference testConference = new Conference(ID_OF_DRAFT_IN_DATABASE, "name", "title", 100,
                ConferenceStatus.DRAFT, null, CONFERENCE_TIMESLOT);
        Conference noRelation = new Conference(ID_OUTSIDE_DATABASE, "name", "title", 100,
                ConferenceStatus.DRAFT, null, CONFERENCE_TIMESLOT);
        Speech speech = new Speech(2L, "Title", CORRECT_TIMESLOT, testConference, new HashSet<>());
        Speech withoutConference = new Speech(1L, "Test", CORRECT_TIMESLOT, noRelation, new HashSet<>());
        SpeechRepository repository = mock(SpeechRepository.class);
        when(repository.save(any())).thenAnswer(a -> a.getArgument(0));
        when(repository.findById(2L)).thenReturn(Optional.of(speech));
        when(repository.findById(1L)).thenReturn(Optional.of(withoutConference));
        testConference.addSpeech(speech);
        ConferenceRepository conferenceRepository = mock(ConferenceRepository.class);
        when(conferenceRepository.findById(ID_OF_DRAFT_IN_DATABASE)).thenReturn(Optional.of(testConference));
        when(conferenceRepository.findById(ID_OUTSIDE_DATABASE)).thenReturn(Optional.empty());
        SpeechInputValidator speechInputValidator = new SpeechInputValidator(() -> NOW_FOR_TEST);
        SpeechOutputMapper outputMapper = new SpeechOutputMapper(new SpeakerOutputMapper());
        BiFunction<Speech, SpeechInput, Speech> inputMapper = new SpeechMapper();
        SpeakerQueryService speakerQueryService = mock(SpeakerQueryService.class);
        ConferenceOutputMapper conferenceOutputMapper = mock(ConferenceOutputMapper.class);
        underTest = new SpeechCommandService(repository, conferenceRepository, speechInputValidator,
                outputMapper, speakerQueryService, conferenceOutputMapper);
    }

    @Test
    @DisplayName("Should add speech")
    void addSpeech() {
        //given
        SpeechInput speechInput = new SpeechInput("Swimming with boots on ?", CORRECT_TIMESLOT);

        //when
        SpeechOutput result = underTest.createSpeech(ID_OF_DRAFT_IN_DATABASE, speechInput);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo(speechInput.getTitle());
        assertThat(result.getTimeSlot()).isEqualTo(speechInput.getTimeSlot());
    }

    @Test
    @DisplayName("Should throw exception when conference is not found in database")
    void noConference() {
        //given
        SpeechInput speechInput = new SpeechInput("Swimming with boots on ?", CORRECT_TIMESLOT);

        //when
        Executable lambdaUnderTest = () -> underTest.createSpeech(ID_OUTSIDE_DATABASE, speechInput);

        //then
        assertThrows(ConferenceNotFoundException.class, lambdaUnderTest);
    }

    @Test
    @DisplayName("Should throw exception when speech is not found in database while adding speakers")
    void noSpeech() {
        //given
        SpeechSpeakersInput speechSpeakersInput = new SpeechSpeakersInput(
                new TreeSet<>(Arrays.asList(1L, 2L, 3L)));

        //when
        Executable lambdaUnderTest = () -> underTest.addSpeakers(ID_OF_DRAFT_IN_DATABASE, 223L,
                speechSpeakersInput);

        //then
        assertThrows(SpeechNotFoundException.class, lambdaUnderTest);
    }

    @Test
    @DisplayName("Should throw speech relation exception while adding speakers to speech in other conference")
    void noRelationSpeechConference() {
        //given
        SpeechSpeakersInput speechSpeakersInput = new SpeechSpeakersInput(
                new TreeSet<>(Arrays.asList(1L, 2L, 3L)));

        //when
        Executable lambdaUnderTest = () -> underTest.addSpeakers(ID_OF_DRAFT_IN_DATABASE, 1L,
                speechSpeakersInput);

        //then
        SpeechRelationException exception = assertThrows(SpeechRelationException.class, lambdaUnderTest);
        assertThat(exception.getMessage()).isEqualTo("The speech is not related to the conference");
    }

}