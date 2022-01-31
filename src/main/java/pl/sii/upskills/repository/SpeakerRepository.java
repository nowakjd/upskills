package pl.sii.upskills.repository;

import org.springframework.data.repository.CrudRepository;
import pl.sii.upskills.model.Speaker;

public interface SpeakerRepository extends CrudRepository<Speaker, Long> {
}
