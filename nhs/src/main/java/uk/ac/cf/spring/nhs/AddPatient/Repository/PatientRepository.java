package uk.ac.cf.spring.nhs.AddPatient.Repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uk.ac.cf.spring.nhs.AddPatient.Entity.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
}