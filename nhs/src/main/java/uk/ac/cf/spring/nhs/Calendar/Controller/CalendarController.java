package uk.ac.cf.spring.nhs.Calendar.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.cf.spring.nhs.Calendar.Repositories.Calendar;
import uk.ac.cf.spring.nhs.Calendar.Repositories.CalendarRepository;
import uk.ac.cf.spring.nhs.Common.util.DeviceDetector;

@Controller
public class CalendarController {
    @GetMapping("/calendar")
    public ModelAndView Calendar(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        if (DeviceDetector.isMobile(request)) {
            modelAndView.setViewName("mobile/Calendar/calendar");
        } else {
            modelAndView.setViewName("desktop/calendar");

        }
        return modelAndView;
    }

    @GetMapping("/mobileaddappt")
    public ModelAndView getMobileAddAppt(){
        ModelAndView modelAndView = new ModelAndView("mobile/Calendar/addappointment");
        return modelAndView;
    }

    @PostMapping("/calendar")
    public ModelAndView appointmentSent(@Valid @ModelAttribute("addappointment")Calendar calendar, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("mobileaddappt", model.asMap());
            return modelAndView;
        } else {
            Calendar calendar = new Calendar(calendar.getAppt_date(), calendar.getAppt_time(), calendar.getAppt_type(), calendar.getAppt_provider(), calendar.getAppt_info());
            CalendarRepository.addAppointment(calendar);

        }
    }
}