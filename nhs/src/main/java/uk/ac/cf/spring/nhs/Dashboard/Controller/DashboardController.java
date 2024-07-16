package uk.ac.cf.spring.nhs.Dashboard.Controller;

import uk.ac.cf.spring.nhs.Common.util.DeviceDetector;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
@Controller
public class DashboardController {
    
    @GetMapping("/dashboard")
    public String dashboard(HttpServletRequest request) {
        if (DeviceDetector.isMobile(request)) {
            return "mobile/dashboard";
        } else {
            return "desktop/dashboard";
        }
    }
}   
