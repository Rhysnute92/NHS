package uk.ac.cf.spring.nhs.AddPatient.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import uk.ac.cf.spring.nhs.AddPatient.Service.PatientService;
import uk.ac.cf.spring.nhs.Security.AuthenticationFacade;
import uk.ac.cf.spring.nhs.Security.UserCredentials.UserCredentials;
import uk.ac.cf.spring.nhs.Security.UserCredentials.UserCredentialsRepository;
import uk.ac.cf.spring.nhs.Security.CustomUserDetails;


@Controller
public class PasswordSetupController {

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private UserCredentialsRepository userCredentialsRepository;

    @Autowired
    private PatientService patientService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/resetrequest")
    public String passwordResetRequest(String email){
        Object principal = authenticationFacade.getAuthentication().getPrincipal();
        Long userId = ((CustomUserDetails)principal).getUserId();
        patientService.passwordReset(email, userId);
        return "redirect:/account?pwupdt";
    }

    @GetMapping("/reset-password")
    public String showPasswordSetupForm(@RequestParam("token") String token, Model model) {
        model.addAttribute("token", token);
        return "login/desktop/resetPassword";
    }

    @PostMapping("/reset-password")
    public String setupPassword(
            @RequestParam("token") String token,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            Model model
    ) {
        // Basic debugging statements to make sure the token is passed through correctly
        System.out.println("Token received: " + token);

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            model.addAttribute("token", token);
            return "login/desktop/resetPassword";
        }

        //TODO Add validation following this for if passwords do not match - popups

        UserCredentials userCredentials = userCredentialsRepository.findByPasswordSetupToken(token);

        if (userCredentials == null) {
            System.out.println("No user found with token: " + token);
            model.addAttribute("error", "Invalid token.");
            return "login/desktop/resetPassword";
        }

        //TODO Add error page / popup for invalid token / invalid link

        String encodedPassword = passwordEncoder.encode(newPassword);
        userCredentialsRepository.updateUserPasswordAndToken(encodedPassword, null, userCredentials.getUserId());

        // Basic debugging statements to make sure the password is reset and the associated userId
        System.out.println("Password reset for userId: " + userCredentials.getUserId());
        model.addAttribute("message", "Password set successfully.");
        return "admin/desktop/success";
    }
}



