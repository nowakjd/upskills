package pl.sii.upskills.speaker.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

/**
SpeakerRepository is a repository.
 */
public interface SpeakerRepository extends JpaRepository<Speaker, Long> {
}
