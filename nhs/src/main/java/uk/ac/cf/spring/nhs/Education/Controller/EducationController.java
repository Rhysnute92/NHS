package uk.ac.cf.spring.nhs.Education.Controller;
import uk.ac.cf.spring.nhs.Common.util.DeviceDetector;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import uk.ac.cf.spring.nhs.Common.util.NavMenuItem;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class EducationController {

    @ModelAttribute("navMenuItems")
    public List<NavMenuItem> navMenuItems() {
        return List.of(
            new NavMenuItem("What is lymphoedema?", "/information", "fa-solid fa-book"),
            new NavMenuItem("Cellulitis", "/cellulitis", "fa-solid fa-book"),
            new NavMenuItem("Treatment", "/treatment", "fa-solid fa-user-check"),
            new NavMenuItem("Helpful resources", "/resources", "fa-solid fa-camera")
        );
    }

    @GetMapping("/information")
    public String education(Model model, HttpServletRequest request) {
        if (DeviceDetector.isMobile(request)) {
            return "education/mobile/information";
        } else {
            return "education/desktop/information";
        }
    }

    @GetMapping("/treatment")
    public String treatment(Model model, HttpServletRequest request) {
        if(DeviceDetector.isMobile(request)){
            return "education/mobile/treatment";
        } else {
            return "education/desktop/treatment";
        }
    }

    @GetMapping("/cellulitis")
    public String cellulitis(Model model, HttpServletRequest request) {
        if (DeviceDetector.isMobile(request)) {
            return "education/mobile/infoCell";
        } else {
            return "education/desktop/infoCell";
        }
    }

    @GetMapping("/resources")
    public String res(Model model, HttpServletRequest request) {
        if (DeviceDetector.isMobile(request)) {
            return "education/mobile/infoRes";
        } else {
            return "education/desktop/infoRes";
        }
    }

    @GetMapping("/treatmentLeg")
    public String leg(Model model, HttpServletRequest request) {
        if (DeviceDetector.isMobile(request)) {
            return "education/mobile/treatmentLeg";
        } else {
            return "education/desktop/treatmentLeg";
        }
    }

    @GetMapping("/treatmentArm")
    public String arm(Model model, HttpServletRequest request) {
        if (DeviceDetector.isMobile(request)) {
            return "education/mobile/treatmentArm";
        } else {
            return "education/desktop/treatmentArm";
        }
    }
    @GetMapping("/treatmentHead")
    public String spec(Model model, HttpServletRequest request) {
        if (DeviceDetector.isMobile(request)) {
            return "education/mobile/treatmentHead";
        } else {
            return "education/desktop/treatmentHead";
        }
    }

}
