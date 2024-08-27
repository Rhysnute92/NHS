package uk.ac.cf.spring.nhs.Symptom.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uk.ac.cf.spring.nhs.Measurement.Entity.Measurement;
import uk.ac.cf.spring.nhs.Symptom.Entity.Symptom;

import java.time.LocalDate;
import java.util.List;

public interface SymptomRepository extends JpaRepository<Symptom, Integer> {
    List<Symptom> findByUserIdAndNameAndDateBetween(Long userId, String symptomType, LocalDate startDate, LocalDate endDate);

    @Query("SELECT DISTINCT s.name FROM Symptom s WHERE s.userId = :userId")
    List<String> findDistinctNamesByUserId(Long userId);
}
