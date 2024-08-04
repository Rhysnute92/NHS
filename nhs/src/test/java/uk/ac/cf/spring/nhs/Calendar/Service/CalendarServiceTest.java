package uk.ac.cf.spring.nhs.Calendar.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uk.ac.cf.spring.nhs.Appointments.Model.Appointments;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CalendarServiceTest {

    private CalendarService calendarService;

    @BeforeEach
    void setUp() {

        calendarService = new CalendarService();
    }

    @Test
    void getCalendarNotEmptyList() {
        List<Appointments> calendarEntries =   calendarService.getCalendarEntries();
        assertFalse(calendarEntries.isEmpty());
    }

    @Test
    void getCalendarNotNullValues() {
        List<Appointments> calendarEntries = calendarService.getCalendarEntries();
        for (Appointments calendarEntry : calendarEntries) {
            assertNotNull(calendarEntry.getInfo());
        }
    }
}
