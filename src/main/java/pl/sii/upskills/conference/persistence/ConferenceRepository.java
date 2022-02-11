package pl.sii.upskills.conference.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 Repository providing CRUD methods.
 */

public interface ConferenceRepository extends JpaRepository<Conference, UUID> {
}
