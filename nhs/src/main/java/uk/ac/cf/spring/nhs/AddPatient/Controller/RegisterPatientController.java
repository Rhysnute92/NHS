package uk.ac.cf.spring.nhs.AddPatient.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.ac.cf.spring.nhs.AddPatient.DTO.RegisterRequest;
import uk.ac.cf.spring.nhs.AddPatient.Service.PatientService;

@Controller
@RequestMapping("/api")
public class RegisterPatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping("/register")
    public String registerPatient(@ModelAttribute RegisterRequest request, RedirectAttributes redirectAttributes) {
        patientService.registerPatient(request);
        return "redirect:/patientprofile/info";
    }
}