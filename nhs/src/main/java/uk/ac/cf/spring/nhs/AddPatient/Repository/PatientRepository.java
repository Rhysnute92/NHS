package uk.ac.cf.spring.nhs.AddPatient.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.ac.cf.spring.nhs.AddPatient.Entity.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findById(long userId);
}