package uk.ac.cf.spring.nhs.Calendar.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.cf.spring.nhs.Appointments.Model.Appointment;
import uk.ac.cf.spring.nhs.Appointments.Service.AppointmentService;
import uk.ac.cf.spring.nhs.Common.util.DeviceDetector;
import uk.ac.cf.spring.nhs.Common.util.NavMenuItem;
import uk.ac.cf.spring.nhs.Security.CustomUserDetails;

import java.util.List;

@Controller
public class CalendarController {

    @Autowired
    private AppointmentService appointmentService;
    /**
     * Get to the calendar part of the application
     * @param request
     * @return
     */
    @GetMapping("/calendar")
    public ModelAndView Calendar(HttpServletRequest request,
                                 @AuthenticationPrincipal CustomUserDetails userDetails) {
        ModelAndView modelAndView = new ModelAndView();

        Long userId = userDetails.getUserId();
        List<Appointment> appointments = appointmentService.getAppointmentsByUserId(Math.toIntExact(userId));
        modelAndView.addObject("appointments", appointments);

        if (DeviceDetector.isMobile(request)) {
            modelAndView.setViewName("calendar/mobile/calendar");
        } else {
            modelAndView.setViewName("calendar/desktop/calendar");

        }
        return modelAndView;
    }

    /**
     * get to the add appointment part of the website
     * @param request
     * @return
     */
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

    /**
     * get to the schedule part for the mobile site
      * @param request
     * @return
     */
    @GetMapping("/schedule")
    public ModelAndView getSchedule(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        if (DeviceDetector.isMobile(request)) {
            modelAndView.setViewName("calendar/mobile/schedule");
        }
        return modelAndView;
    }


    @ModelAttribute("navMenuItems")
    public List<NavMenuItem> navMenuItems() {
        return List.of(
                new NavMenuItem("Calendar", "/calendar", "fa-regular fa-calendar-days"),
                new NavMenuItem("Add Appointment", "/addappointment", "fa-solid fa-calendar-check")
        );
    }

}