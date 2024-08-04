package uk.ac.cf.spring.nhs.Account;
import uk.ac.cf.spring.nhs.Common.util.DeviceDetector;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AccountController {

    @GetMapping("/account")
    public String account(HttpServletRequest request) {
        if (DeviceDetector.isMobile(request)) {
            return "account/account";
        } else {
            return "account/account";
        }
    }
}
