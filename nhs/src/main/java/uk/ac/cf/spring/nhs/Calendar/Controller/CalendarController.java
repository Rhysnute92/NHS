package uk.ac.cf.spring.nhs.Calendar.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.cf.spring.nhs.Calendar.Repositories.CalendarRepository;
import uk.ac.cf.spring.nhs.Calendar.Repositories.Calendar;
import uk.ac.cf.spring.nhs.Common.util.DeviceDetector;
import uk.ac.cf.spring.nhs.Common.util.NavMenuItem;

import java.util.List;

import java.util.Calendar;
import java.util.List;

@Controller
public class CalendarController {

    @Autowired
    private CalendarRepository calendarRepository;

    @GetMapping("/calendar")
    public ModelAndView Calendar(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        if (DeviceDetector.isMobile(request)) {
            modelAndView.setViewName("calendar/mobile/calendar");
        } else {
            modelAndView.setViewName("calendar/desktop/calendar");

        }
        return modelAndView;
    }

    @GetMapping("/mobileaddappt")
    public ModelAndView getMobileAddAppt(){
        ModelAndView modelAndView = new ModelAndView("calendar/mobile/addappointment");
        return modelAndView;
    }

    @PostMapping("/calendar")
    public ModelAndView appointmentSent(@Valid @ModelAttribute("addappointment") Calendar calendar, BindingResult bindingResult, Model model) {
        ModelAndView modelAndView = new ModelAndView("/mobile/Calendar/calendar");
        if (bindingResult.hasErrors()) {
            Calendar cal = new Calendar(calendar.getAppointmentTime(), calendar.getAppointmentProvider());
            System.out.println(cal);
            calendarRepository.getAllAppointments();
            List<uk.ac.cf.spring.nhs.Calendar.Repositories.Calendar> calendars = calendarRepository.getAllAppointments();
            modelAndView.addObject("calendar", calendar);
        } else {
            Calendar cal = new Calendar(calendar.getAppointmentTime(), calendar.getAppointmentProvider());
            System.out.println(cal);
            calendarRepository.getAllAppointments();
            List<uk.ac.cf.spring.nhs.Calendar.Repositories.Calendar> calendars = calendarRepository.getAllAppointments();
            modelAndView.addObject("calendar", calendars);
        }

        @ModelAttribute("navMenuItems")
        public List<NavMenuItem> navMenuItems() {
            return List.of(
                    new NavMenuItem("Schedule", "/calendar", "fa-regular fa-calendar-days"),
                    new NavMenuItem("Add Appointment", "/mobileaddappt", "fa-solid fa-calendar-check")
            );
        }
    }
}