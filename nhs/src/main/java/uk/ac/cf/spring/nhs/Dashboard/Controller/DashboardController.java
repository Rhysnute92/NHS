package uk.ac.cf.spring.nhs.Dashboard.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import uk.ac.cf.spring.nhs.AddPatient.Entity.Patient;
import uk.ac.cf.spring.nhs.AddPatient.Service.PatientService;
import uk.ac.cf.spring.nhs.Security.AuthenticationInterface;
import uk.ac.cf.spring.nhs.Security.CustomUserDetails;
import uk.ac.cf.spring.nhs.UserTask.Model.UserTask;
import uk.ac.cf.spring.nhs.UserTask.Service.UserTaskService;

@Controller
public class DashboardController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private AuthenticationInterface authenticationFacade;

    @Autowired
    private UserTaskService userTaskService;

    @GetMapping("/dashboard")
    public String dashboard(HttpServletRequest request, Model model) {
        // Get principal object from the authentication
        // Principal contains the UserDetails object for the current user
        Object principal = authenticationFacade.getAuthentication().getPrincipal();
        // Specify principle as CustomUserDetails and extract data using class functions
        Long userId = ((CustomUserDetails) principal).getUserId();
        Patient patient = patientService.findPatientbyId(userId);
        String fullname = patientService.getFullname(patient);

        model.addAttribute("patientName", fullname);

        return "dashboard/dashboard";
    }

}
