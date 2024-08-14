package uk.ac.cf.spring.nhs.Account;
import uk.ac.cf.spring.nhs.AddPatient.Entity.Patient;
import uk.ac.cf.spring.nhs.AddPatient.Service.PatientService;
import uk.ac.cf.spring.nhs.Common.util.DeviceDetector;
import uk.ac.cf.spring.nhs.Security.AuthenticationInterface;
import uk.ac.cf.spring.nhs.Security.CustomUserDetails;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


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
        String userId = ((CustomUserDetails)principal).getUsername();
        return userId;
    }

    
    @GetMapping("/patientProfile")
    @ResponseBody
    public PatientProfileDTO patientProfile(){
        Object principal = authenticationFacade.getAuthentication().getPrincipal();
        Long userId = Long.parseLong(((CustomUserDetails)principal).getUsername());
        PatientProfileDTO profile = patientService.profile(userId);
        return profile;
    }
}
