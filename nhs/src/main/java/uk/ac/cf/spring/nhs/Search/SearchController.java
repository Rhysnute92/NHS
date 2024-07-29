package uk.ac.cf.spring.nhs.Search;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import uk.ac.cf.spring.nhs.Common.util.DeviceDetector;

@Controller
public class SearchController {
    @GetMapping("/search")
    public String showGuestLandingPage(HttpServletRequest request) {
        if (DeviceDetector.isMobile(request)) {
            return "mobile/search";
        } else {
            return "desktop/search";
        }
    }
}