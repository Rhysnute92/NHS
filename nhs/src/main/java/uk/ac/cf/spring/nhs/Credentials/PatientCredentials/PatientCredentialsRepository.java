package uk.ac.cf.spring.nhs.Credentials.PatientCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientCredentialsRepository extends JpaRepository<PatientCredentials, Long> {
    
}
