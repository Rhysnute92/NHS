package uk.ac.cf.spring.nhs.Guest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import uk.ac.cf.spring.nhs.Common.util.DeviceDetector;

@Controller
public class GuestController {
    @GetMapping("/guest")
    public String showGuestLandingPage(HttpServletRequest request) {
        if (DeviceDetector.isMobile(request)) {
            return "/mobile/guestLanding";
        } else {
            return "/desktop/guestlanding";
        }
    }

    @GetMapping("/landing")
    public String showLandingPage(HttpServletRequest request) {
        if (DeviceDetector.isMobile(request)) {
            return "/mobile/landing";
        } else {
            return "/desktop/guestLanding";
        }
    }

    @GetMapping("/guest/appointment")
    public String showGuestAppointmentPage() {return "/mobile/guestAppointment";}

    @GetMapping("/guest/services")
    public String showGuestServicesPage() {return "/mobile/guestServices";}

    @GetMapping("/guest/find")
    public String showGuestFindUsPage() {return "/mobile/guestFindUs";}

}

