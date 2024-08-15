package uk.ac.cf.spring.nhs.Security.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Long> {
    UserCredentials findByUserName(String name);
    UserCredentials findByPasswordSetupToken(String passwordSetupToken);
}
