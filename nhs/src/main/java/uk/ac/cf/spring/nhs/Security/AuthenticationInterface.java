package uk.ac.cf.spring.nhs.Security;

import org.springframework.security.core.Authentication;

//Interface for accessing the current session user
//To use need to @Autowire it in the class and create and Authentication object
//by calling the getAuthentication() function.
//After that the User Details functions can be used on the object to retrieve specific data.
public interface AuthenticationInterface {
    Authentication getAuthentication();
}
