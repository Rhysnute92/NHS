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
            return "guest/mobile/guestLanding";
        } else {
            return "guest/desktop/guestLanding";
        }
    }

    @GetMapping("/landing")
    public String showLandingPage(HttpServletRequest request) {
        if (DeviceDetector.isMobile(request)) {
            return "guest/mobile/landing";
        } else {
            return "guest/desktop/guestLanding";
        }
    }

    @GetMapping("/guest/appointment")
    public String showGuestAppointmentPage() {return "guest/mobile/guestAppointment";}

    @GetMapping("/guest/services")
    public String showGuestServicesPage() {return "guest/mobile/guestServices";}

    @GetMapping("/guest/find")
    public String showGuestFindUsPage() {return "guest/mobile/guestFindUs";}

}

