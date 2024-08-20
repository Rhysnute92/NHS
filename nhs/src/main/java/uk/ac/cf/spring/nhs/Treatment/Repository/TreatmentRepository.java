package uk.ac.cf.spring.nhs.Treatment.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.ac.cf.spring.nhs.Treatment.Entity.Treatment;

public interface TreatmentRepository extends JpaRepository<Treatment, Integer> {
}
