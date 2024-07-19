package uk.ac.cf.spring.nhs.Education.Controller;
import uk.ac.cf.spring.nhs.Common.util.DeviceDetector;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class EducationController {

    @GetMapping("/information")
    public String education(HttpServletRequest request) {
        if (DeviceDetector.isMobile(request)) {
            return "mobile/information";
        } else {
            return "desktop/Education/information";
        }
    }

    //Controllers for temporary pages
    @GetMapping("/cellulitis")
    public String cellulitis(HttpServletRequest request) {
        if (DeviceDetector.isMobile(request)) {
            return "mobile/Education/infoCell";
        } else {
            return "desktop/Education/infoCell";
        }
    }

    @GetMapping("/resources")
    public String resources(HttpServletRequest request) {
        if (DeviceDetector.isMobile(request)) {
            return "mobile/Education/infoRes";
        } else {
            return "desktop/Education/infoRes";
        }
    }

}
