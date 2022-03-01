package pl.sii.upskills.speaker.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository providing CRUD methods.
 */
public interface SpeakerRepository extends JpaRepository<Speaker, Long> {
    List<Speaker> findBySpeakerStatus(SpeakerStatus speakerStatus);
}

