package uk.ac.cf.spring.nhs.Security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

//Custom class for retrieving current session user data anywhere in the application
@Component
public class AuthenticationFacade implements AuthenticationInterface {

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
