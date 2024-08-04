package uk.ac.cf.spring.nhs.Calendar.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.ac.cf.spring.nhs.Calendar.Model.Calendar;
import uk.ac.cf.spring.nhs.Calendar.Repositories.CalendarRepository;

@Service
public class CalendarService {

    @Autowired
    private CalendarRepository calendarRepository;

    public List<Calendar> getAllCalendars() {
        return calendarRepository.findAll();
    }

    public Calendar getCalendarById(Integer id) {
        return calendarRepository.findById(id).orElse(null);
    }

    public Calendar getCalendarByUserId(Integer userId) {
        return calendarRepository.findByUserId(userId);
    }

    public Calendar saveCalendar(Calendar calendar) {
        return calendarRepository.save(calendar);
    }

    public void deleteCalendar(Integer id) {
        calendarRepository.deleteById(id);
    }
}
