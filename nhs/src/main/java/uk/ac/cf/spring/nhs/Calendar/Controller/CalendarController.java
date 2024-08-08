package uk.ac.cf.spring.nhs.Calendar.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import uk.ac.cf.spring.nhs.Common.util.NavMenuItem;

@Controller
@RequestMapping("/calendar")
public class CalendarController {


    @ModelAttribute("navMenuItems")
    public List<NavMenuItem> navMenuItems() {
        return List.of(
                new NavMenuItem("Schedule", "/calendar", "fa-regular fa-calendar-days"),
                new NavMenuItem("Add Appointment", "/mobileaddappt", "fa-solid fa-calendar-check"));
    }
}
