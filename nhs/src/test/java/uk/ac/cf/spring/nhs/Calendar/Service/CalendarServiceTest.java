package uk.ac.cf.spring.nhs.Calendar.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uk.ac.cf.spring.nhs.CalendarEntry.Model.CalendarEntry;

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
        List<CalendarEntry> calendarEntries =   calendarService.getCalendarEntries();
        assertFalse(calendarEntries.isEmpty());
    }

    @Test
    void getCalendarNotNullValues() {
        List<CalendarEntry> calendarEntries = calendarService.getCalendarEntries();
        for (CalendarEntry calendarEntry : calendarEntries) {
            assertNotNull(calendarEntry.getInfo());
        }
    }
}
