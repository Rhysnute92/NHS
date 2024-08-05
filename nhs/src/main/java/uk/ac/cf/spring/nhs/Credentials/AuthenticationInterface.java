package uk.ac.cf.spring.nhs.Credentials;

import org.springframework.security.core.Authentication;

public interface AuthenticationInterface {
    Authentication getAuthentication();
}
