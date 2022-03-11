package pl.sii.upskills.speech.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository providing CRUD methods.
 */
public interface SpeechRepository extends JpaRepository<Speech, Long> {
}
