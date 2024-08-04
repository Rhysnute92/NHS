package uk.ac.cf.spring.nhs.Calendar.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uk.ac.cf.spring.nhs.Calendar.Model.Calendar;
import uk.ac.cf.spring.nhs.Calendar.Service.CalendarService;
import uk.ac.cf.spring.nhs.Common.util.NavMenuItem;

@RestController
@RequestMapping("/calendar")
public class CalendarController {

    @Autowired
    private CalendarService calendarService;

    @GetMapping
    public Calendar getCalendarForCurrentUser() {
        Integer userId = 1; // TODO: get current user ID properly when implemented

        return calendarService.getCalendarByUserId(userId);
    }

    @PostMapping
    public Calendar createCalendar(@RequestBody Calendar calendar) {
        return calendarService.saveCalendar(calendar);
    }

    @DeleteMapping("/{id}")
    public void deleteCalendar(@PathVariable Integer id) {
        calendarService.deleteCalendar(id);
    }

    @ModelAttribute("navMenuItems")
    public List<NavMenuItem> navMenuItems() {
        return List.of(
                new NavMenuItem("Schedule", "/calendar", "fa-regular fa-calendar-days"),
                new NavMenuItem("Add Appointment", "/mobileaddappt", "fa-solid fa-calendar-check"));
    }
}
