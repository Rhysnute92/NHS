package uk.ac.cf.spring.nhs.AddPatient.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uk.ac.cf.spring.nhs.Security.UserCredentials.UserCredentials;
import uk.ac.cf.spring.nhs.Security.UserCredentials.UserCredentialsRepository;


@Controller
public class PasswordSetupController {

    @Autowired
    private UserCredentialsRepository userCredentialsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            model.addAttribute("token", token);
            return "login/desktop/resetPassword";
        }
        //TODO Add validation following this for if passwords do not match - popups

        UserCredentials userCredentials = userCredentialsRepository.findByPasswordSetupToken(token);

        if (userCredentials != null) {
            String encodedPassword = passwordEncoder.encode(newPassword);
            userCredentials.setUserPassword(encodedPassword);
            userCredentials.setPasswordSetupToken(null);
            userCredentialsRepository.save(userCredentials);
            model.addAttribute("message", "Password set successfully.");
            return "admin/desktop/success";
        } else {
            model.addAttribute("error", "Invalid token.");
            return "login/desktop/resetPassword";
        }
        //TODO Add error page / popup for invalid token / invalid link
    }
}


