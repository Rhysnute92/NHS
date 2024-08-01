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
            return "education/mobile/information";
        } else {
            return "education/desktop/information";
        }
    }

    @GetMapping("/treatment")
    public String treatment(HttpServletRequest request) {
        if(DeviceDetector.isMobile(request)){
            return "education/mobile/treatment";
        } else {
            return "education/desktop/treatment";
        }
    }

    //Controllers for temporary pages
    @GetMapping("/cellulitis")
    public String cellulitis(HttpServletRequest request) {
        if (DeviceDetector.isMobile(request)) {
            return "education/mobile/infoCell";
        } else {
            return "education/desktop/infoCell";
        }
    }

    @GetMapping("/resources")
    public String res(HttpServletRequest request) {
        if (DeviceDetector.isMobile(request)) {
            return "education/mobile/infoRes";
        } else {
            return "education/desktop/infoRes";
        }
    }

    @GetMapping("/treatmentSpec")
    public String spec(HttpServletRequest request) {
        if (DeviceDetector.isMobile(request)) {
            return "education/mobile/treatmentSpec";
        } else {
            return "education/desktop/treatmentSpec";
        }
    }

}
