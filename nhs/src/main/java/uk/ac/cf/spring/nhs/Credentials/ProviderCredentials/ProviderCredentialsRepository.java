package uk.ac.cf.spring.nhs.Credentials.ProviderCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderCredentialsRepository extends JpaRepository<ProviderCredentials, Long> {
    
}
