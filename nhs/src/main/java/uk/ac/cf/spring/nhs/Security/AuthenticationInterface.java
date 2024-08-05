package uk.ac.cf.spring.nhs.Security;

import org.springframework.security.core.Authentication;

public interface AuthenticationInterface {
    Authentication getAuthentication();
}
