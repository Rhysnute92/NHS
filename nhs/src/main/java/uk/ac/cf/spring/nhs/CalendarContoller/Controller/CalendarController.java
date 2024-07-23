package uk.ac.cf.spring.nhs.CalendarContoller.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.cf.spring.nhs.Common.util.DeviceDetector;

@Controller
public class CalendarController {
    @GetMapping("/calendar")
    public ModelAndView Calendar(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        if (DeviceDetector.isMobile(request)) {
            modelAndView.setViewName("mobile/calendar");
        } else {
            modelAndView.setViewName("desktop/calendar");

        }
        return modelAndView;
    }

    @GetMapping("mobileaddappt")
    public ModelAndView getMobileAddAppt(){
        ModelAndView modelAndView = new ModelAndView("mobile/addappointment");
        return modelAndView;
    }
}