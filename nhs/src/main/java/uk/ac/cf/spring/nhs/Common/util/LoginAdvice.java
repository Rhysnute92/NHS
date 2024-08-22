package uk.ac.cf.spring.nhs.Common.util;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import uk.ac.cf.spring.nhs.Security.CustomUserDetails;

@ControllerAdvice
public class LoginAdvice {

    @ModelAttribute("isLoggedIn")
    public boolean getLoggedIn(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return userDetails != null;
    }
}
