package uk.ac.cf.spring.nhs.Dashboard.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import uk.ac.cf.spring.nhs.Common.util.DeviceDetector;

@Controller
public class DashboardController {

    /**
     * Handles the GET request to the "/dashboard" endpoint and returns the
     * appropriate view based on the device type.
     *
     * @param request the HttpServletRequest object representing the HTTP request
     * @return the name of the view to be rendered, either "mobile/dashboard" or
     *         "desktop/dashboard"
     */
    @GetMapping("/dashboard")
    public String dashboard(HttpServletRequest request) {
        if (DeviceDetector.isMobile(request)) {
            return "mobile/Dashboard/dashboard";
        } else {
            return "desktop/Dashboard/dashboard";
        }
    }
}
