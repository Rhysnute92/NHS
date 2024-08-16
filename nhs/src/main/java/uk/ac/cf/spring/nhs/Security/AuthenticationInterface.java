package uk.ac.cf.spring.nhs.Security;

import org.springframework.security.core.Authentication;

//Interface for accessing the current session user
//To use need to @Autowire it in the desired class
//Example use can be seen in AccountController 
public interface AuthenticationInterface {
    Authentication getAuthentication();
    boolean hasRole(String role);
}

