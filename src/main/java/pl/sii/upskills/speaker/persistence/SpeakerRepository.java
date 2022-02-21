package pl.sii.upskills.speaker.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository providing CRUD methods.
 */
public interface SpeakerRepository extends JpaRepository<Speaker, Long> {
}
