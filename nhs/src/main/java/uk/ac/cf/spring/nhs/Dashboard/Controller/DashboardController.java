package uk.ac.cf.spring.nhs.Dashboard.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import uk.ac.cf.spring.nhs.Common.util.DeviceDetector;

@Controller
public class DashboardController {

    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

    @GetMapping("/dashboard")
    public String dashboard(HttpServletRequest request) {
        boolean isMobile = DeviceDetector.isMobile(request);
        logger.info("Device is mobile: " + isMobile);

        if (isMobile) {
            return "mobile/Dashboard/dashboard";
        } else {
            return "desktop/Dashboard/dashboard";
        }
    }
}
