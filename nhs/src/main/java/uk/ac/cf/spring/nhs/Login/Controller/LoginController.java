package uk.ac.cf.spring.nhs.Login.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import uk.ac.cf.spring.nhs.Common.util.DeviceDetector;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage(HttpServletRequest request) {
        if (DeviceDetector.isMobile(request)) {
            return "mobile/login";
        } else {
            return "desktop/login";
        }
    }
}
