package uk.ac.cf.spring.nhs.Security.UserCredentials;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Long> {
    UserCredentials findByUserName(String name);
    UserCredentials findByPasswordSetupToken(String passwordSetupToken);
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("update UserCredentials u set u.userPassword = :pass, u.passwordSetupToken = :token where u.userId = :id")
    void updateUserPasswordAndToken(@Param("pass") String pass, @Param("token") String token, @Param("id") Long id);
}
