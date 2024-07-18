package uk.ac.cf.spring.nhs.CalendarContoller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.cf.spring.nhs.Common.util.DeviceDetector;

@Controller
public class calendarController {
    @GetMapping("/Calendar")
    public ModelAndView Calendar(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        if (DeviceDetector.isMobile(request)) {
            modelAndView.setViewName("Calendar/html/mobileMonth");
        } else {
            modelAndView.setViewName("Calendar/html/Monthly");
        }
        return modelAndView;
    }

    @GetMapping("mobilesched")
    public ModelAndView getMobileSched(){
        ModelAndView modelAndView = new ModelAndView("Calendar/html/mobileSchedule");
        return modelAndView;
    }

    @GetMapping("mobileaddappt")
    public ModelAndView getMobileAddAppt(){
        ModelAndView modelAndView = new ModelAndView("Calendar/html/mobileAddAppt");
        return modelAndView;
    }
}