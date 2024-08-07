package uk.ac.cf.spring.nhs.Management;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ManagementController {
    @GetMapping("/management")
    public String showManagementPage(HttpServletRequest request) {
        return "management/management";
    }
}
