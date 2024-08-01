package uk.ac.cf.spring.nhs.AddPatient;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import uk.ac.cf.spring.nhs.Common.util.DeviceDetector;

@Controller
public class AddPatientController {
    @GetMapping("/addpatient")
    public String showAddPatientPage(HttpServletRequest request) {
        if (DeviceDetector.isMobile(request)) {
            return "admin/mobile/addPatient";
        } else {
            return "admin/desktop/addPatient";
        }
    }
}