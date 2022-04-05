package pl.sii.upskills.speaker.service.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sii.upskills.speaker.persistence.Speaker;
import pl.sii.upskills.speaker.persistence.SpeakerRepository;
import pl.sii.upskills.speaker.service.model.SpeakerInput;
import pl.sii.upskills.speaker.service.model.SpeakerOutput;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.sii.upskills.speaker.persistence.SpeakerStatus.ACTIVE;

@SpringBootTest
class SpeakerServiceITTest {

    @Autowired
    SpeakerCommandService underTest;
    @Autowired
    private SpeakerRepository speakerRepository;

    @Test
    @DisplayName("Should add speaker to database")
    void happyPath() {
        // given
        SpeakerInput speakerInput = new SpeakerInput("John", "Doe", "123456789",
                "john@email.com", "My bio");

        // when
        SpeakerOutput speakerOutput = underTest.addSpeaker(speakerInput);

        // then
        assertThat(speakerOutput.getId()).isNotNull();
        assertThat(speakerOutput.getEmail()).isEqualTo(speakerInput.getEmail());
    }

    @Test
    @DisplayName("Should update speaker in database")
    void updatingSpeaker() {
        // given
        insertSpeakers();
        SpeakerInput speakerInput = new SpeakerInput("John", "Doe", "123456789",
                "john@email.com", "My bio");

        // when
        SpeakerOutput speakerOutput = underTest.updateSpeaker(2L, speakerInput);

        // then
        assertThat(speakerOutput.getId()).isEqualTo(2L);
        assertThat(speakerOutput.getFirstName()).isEqualTo(speakerInput.getFirstName());
        assertThat(speakerOutput.getLastName()).isEqualTo(speakerInput.getLastName());
        assertThat(speakerOutput.getPhoneNumber()).isEqualTo(speakerInput.getPhoneNumber());
        assertThat(speakerOutput.getBio()).isEqualTo(speakerInput.getBio());
        assertThat(speakerOutput.getEmail()).isEqualTo(speakerInput.getEmail());
    }

    private void insertSpeakers() {
        speakerRepository.save(new Speaker(1L, "John", "Doe", "128345679",
                "john.doe@gmail.com", "My bio", ACTIVE));
        speakerRepository.save(new Speaker(2L, "James", "King", "128355555",
                "james.king@gmail.com", "My short bio", ACTIVE));
        speakerRepository.save(new Speaker(3L, "Jane", "Doe", "455699322",
                "mymail@gmail.com", "My not so short bio", ACTIVE));
    }
}
