package uk.ac.cf.spring.nhs.Diary.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.ac.cf.spring.nhs.Diary.Entity.Symptom;

public interface SymptomRepository extends JpaRepository<Symptom, Integer> {
}
