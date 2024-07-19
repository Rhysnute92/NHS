package uk.ac.cf.spring.nhs.Education.Controller;
import uk.ac.cf.spring.nhs.Common.util.DeviceDetector;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class EducationController {

    @GetMapping("/information")
    public String dashboard(HttpServletRequest request) {
        if (DeviceDetector.isMobile(request)) {
            return "mobile/Education/information";
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
    public String res(HttpServletRequest request) {
        if (DeviceDetector.isMobile(request)) {
            return "mobile/Education/infoRes";
        } else {
            return "desktop/Education/infoRes";
        }
    }

}
