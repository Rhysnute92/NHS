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
    Patient findById(long userId);

    @Query(value = "SELECT * FROM Patients p WHERE p.NHSNumber LIKE :nhsNumber", nativeQuery = true)
    Patient findByNHSNumber(@Param("nhsNumber") String nhsNumber);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("update Patient u set u.patientTitle = :title, u.patientName = :name, u.patientLastName = :lastname, u.patientMobile = :mobile, u.patientEmail = :email where u.userId = :id")
    void updatePatient(@Param("title") String title, @Param("name") String name, @Param("lastname") String lastname, @Param("mobile") String mobile, @Param("email") String email, @Param("id") Long id);
}
