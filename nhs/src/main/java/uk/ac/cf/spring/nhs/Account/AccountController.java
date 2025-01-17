package uk.ac.cf.spring.nhs.Account;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import uk.ac.cf.spring.nhs.AddPatient.Entity.Patient;
import uk.ac.cf.spring.nhs.AddPatient.Service.PatientService;
import uk.ac.cf.spring.nhs.Provider.Entity.Provider;
import uk.ac.cf.spring.nhs.Provider.Service.ProviderService;
import uk.ac.cf.spring.nhs.Security.AuthenticationInterface;
import uk.ac.cf.spring.nhs.Security.CustomUserDetails;

@Controller
public class AccountController {

    @Autowired
    private AuthenticationInterface authenticationFacade;

    @Autowired
    private PatientService patientService;

    @Autowired
    private ProviderService providerService;

    @GetMapping("/account")
    public String account(@RequestParam("pwupdt") Optional<Boolean> pwupdt, Model model) {
        if (authenticationFacade.hasRole("ROLE_PATIENT")) {
            Object principal = authenticationFacade.getAuthentication().getPrincipal();
            Long userId = ((CustomUserDetails) principal).getUserId();
            PatientProfileDTO profile = patientService.profile(userId);
            model.addAttribute("patient", profile);
            return "account/accountPatient";
        } else {
            Object principal = authenticationFacade.getAuthentication().getPrincipal();
            Long userId = ((CustomUserDetails) principal).getUserId();
            Provider provider = providerService.getProviderById(userId);
            model.addAttribute("fullname", providerService.getFullName(provider));
            model.addAttribute("occupation", provider.getProviderOccupation());
            return "account/accountProvider";
        }
    }

    @GetMapping("/username")
    @ResponseBody
    public String userFullName() {
        // Get principal object from the authentication
        // Principal contains the UserDetails object for the current user
        Object principal = authenticationFacade.getAuthentication().getPrincipal();
        // Specify principle as CustomUserDetails and extract data using class functions
        String userId = ((CustomUserDetails) principal).getUsername();
        return userId;
    }

    @GetMapping("/userID")
    @ResponseBody
    public Long userID() {
        Object principal = authenticationFacade.getAuthentication().getPrincipal();
        Long userId = ((CustomUserDetails) principal).getUserId();
        return userId;
    }

    public String username() {
        // Get principal object from the authentication
        // Principal contains the UserDetails object for the current user
        Object principal = authenticationFacade.getAuthentication().getPrincipal();
        // Specify principle as CustomUserDetails and extract data using class functions
        String username = ((CustomUserDetails) principal).getUsername();
        return username;
    }

    @GetMapping("/update")
    public String update(@RequestParam("success") Optional<Boolean> success, Model model) {
        if (success.isEmpty()) {
            Object principal = authenticationFacade.getAuthentication().getPrincipal();
            Long userId = ((CustomUserDetails) principal).getUserId();
            PatientProfileDTO patient = patientService.profile(userId);
            model.addAttribute("patient", patient);
            return "account/update";
        } else {
            return "account/update";
        }
    }

    @PostMapping("/updatePatient")
    public String updatePatient(@ModelAttribute UpdateRequest request) {
        Object principal = authenticationFacade.getAuthentication().getPrincipal();
        Long userId = ((CustomUserDetails) principal).getUserId();
        Patient patient = patientService.findPatientbyId(userId);
        patientService.updatePatient(request, patient);
        return "redirect:/update?success";
    }
}
