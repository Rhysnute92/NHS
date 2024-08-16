package uk.ac.cf.spring.nhs.Symptom.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.ac.cf.spring.nhs.Symptom.Entity.Symptom;

public interface SymptomRepository extends JpaRepository<Symptom, Integer> {
}
