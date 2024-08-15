package uk.ac.cf.spring.nhs.Account;
import uk.ac.cf.spring.nhs.AddPatient.Entity.Patient;
import uk.ac.cf.spring.nhs.AddPatient.Service.PatientService;
import uk.ac.cf.spring.nhs.Common.util.DeviceDetector;
import uk.ac.cf.spring.nhs.Security.AuthenticationInterface;
import uk.ac.cf.spring.nhs.Security.CustomUserDetails;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class AccountController {

    @Autowired
    private AuthenticationInterface authenticationFacade;

    @Autowired
    private PatientService patientService;

    @GetMapping("/account")
    public String account() {return "account/account";}

    @GetMapping("/username")
    @ResponseBody
    public String username(){
        //Get principal object from the authentication
        //Principal contains the UserDetails object for the current user
        Object principal = authenticationFacade.getAuthentication().getPrincipal();
        //Specify principle as CustomUserDetails and extract data using class functions
        String username = ((CustomUserDetails)principal).getUsername();
        return username;
    }

    @GetMapping("/patientProfile")
    @ResponseBody
    public PatientProfileDTO patientProfile(){
        Object principal = authenticationFacade.getAuthentication().getPrincipal();
        Long userId = ((CustomUserDetails)principal).getUserId();
        PatientProfileDTO profile = patientService.profile(userId);
        return profile;
    }

    @GetMapping("/update")
    public String update(@RequestParam("success") Optional<Boolean> success, Model model){
        if (success.isEmpty()){
        Object principal = authenticationFacade.getAuthentication().getPrincipal();
        Long userId = ((CustomUserDetails)principal).getUserId();
        Patient patient = patientService.findPatientbyId(userId);
        model.addAttribute("patient", patient);
        return "account/update";
    } else {
        return "account/update";
        }
    }

    @PostMapping("/updatePatient")
    public String updatePatient(@ModelAttribute UpdateRequest request){
        Object principal = authenticationFacade.getAuthentication().getPrincipal();
        Long userId = ((CustomUserDetails)principal).getUserId();
        Patient patient = patientService.findPatientbyId(userId);
        patientService.updatePatient(request, patient);
        return "redirect:/update?success";
    }
}
