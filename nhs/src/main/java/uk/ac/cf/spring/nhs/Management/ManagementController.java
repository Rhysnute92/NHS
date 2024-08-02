package uk.ac.cf.spring.nhs.Management;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import uk.ac.cf.spring.nhs.Common.util.DeviceDetector;

@Controller
public class ManagementController {
    @GetMapping("/management")
    public String showManagementPage(HttpServletRequest request) {
        if (DeviceDetector.isMobile(request)) {
            return "management/mobile/management";
        } else {
            return "management/desktop/management";
        }
    }
}
