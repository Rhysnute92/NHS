import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class calendarController {
    @GetMapping("/Calendar")
    public ModelAndView getCalendar(){
        ModelAndView modelAndView = new ModelAndView("Calendar/html/Monthly");
        return modelAndView;
    }

    @GetMapping("/mobilecal")
    public ModelAndView getMobileCal(){
        ModelAndView modelAndView = new ModelAndView("Calendar/html/mobileMonth");
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