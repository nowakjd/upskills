package pl.sii.upskills.conference.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sii.upskills.conference.service.model.ConferenceOutput;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository providing CRUD methods.
 */

public interface ConferenceRepository extends JpaRepository<Conference, UUID> {
    List<Conference> findByStatus(ConferenceStatus status);
}
