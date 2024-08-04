package uk.ac.cf.spring.nhs.Diary.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.ac.cf.spring.nhs.Diary.Entity.Symptom;

import java.util.List;

@Repository
public interface SymptomRepository extends JpaRepository<Symptom, Integer> {
    List<Symptom> findByUserId(int userId);
}
