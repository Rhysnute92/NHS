package uk.ac.cf.spring.nhs.Account;
import uk.ac.cf.spring.nhs.Common.util.DeviceDetector;
import uk.ac.cf.spring.nhs.Security.AuthenticationInterface;
import uk.ac.cf.spring.nhs.Security.CustomUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AccountController {

    @Autowired
    private AuthenticationInterface authenticationFacade;

    @GetMapping("/account")
    public String account(HttpServletRequest request) {
        if (DeviceDetector.isMobile(request)) {
            return "account/account";
        } else {
            return "account/account";
        }
    }

    @GetMapping("/userFullName")
    @ResponseBody
    public String userFullName(){
        //Get principal object from the authentication
        Object principal = authenticationFacade.getAuthentication().getPrincipal();
        //Specify principle as CustomUserDetails and extract data
        String userId = ((CustomUserDetails)principal).getUsername();
        return userId;
    }
}
