package uk.ac.cf.spring.nhs.Calendar.Controller;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.servlet.ModelAndView;
import uk.ac.cf.spring.nhs.Appointments.Model.Appointment;
import uk.ac.cf.spring.nhs.Appointments.Service.AppointmentService;
import uk.ac.cf.spring.nhs.Common.util.DeviceDetector;
import uk.ac.cf.spring.nhs.Common.util.NavMenuItem;

@Controller
@RequestMapping("/calendar")
public class CalendarController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping
    public String calendar(HttpServletRequest request, Model model) {
        if (DeviceDetector.isMobile(request)) {
            return "calendar/mobile/calendar";
        } else {
            return "calendar/desktop/calendar";
        }
    }

    @GetMapping("/addappointment")
    public ModelAndView getMobileAddAppt(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        if (DeviceDetector.isMobile(request)){
            modelAndView.setViewName("calendar/mobile/addappointment");
        } else {
            modelAndView.setViewName("calendar/desktop/addappointment");
        }
        return modelAndView;
    }

    @ModelAttribute("navMenuItems")
    public List<NavMenuItem> navMenuItems() {
        return List.of(
                new NavMenuItem("Schedule", "/calendar", "fa-regular fa-calendar-days"),
                new NavMenuItem("Add Appointment", "/addappointment", "fa-solid fa-calendar-check")
        );
    }
}