package uk.ac.cf.spring.nhs.AddPatient;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.cf.spring.nhs.AddPatient.DTO.RegisterRequest;
import uk.ac.cf.spring.nhs.AddPatient.Service.PatientService;
import uk.ac.cf.spring.nhs.Common.util.DeviceDetector;

@Controller
public class AddPatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping("/addpatient")
    public String showAddPatientPage(HttpServletRequest request) {
        if (DeviceDetector.isMobile(request)) {
            return "admin/mobile/addPatient";
        } else {
            return "admin/desktop/addPatient";
        }
    }

}
