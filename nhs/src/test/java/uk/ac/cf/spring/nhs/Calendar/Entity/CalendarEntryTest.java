package uk.ac.cf.spring.nhs.Calendar.Entity;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class CalendarEntryTest {

    @Test
    public void testCalendarGetters() {
        Date date = new Date();
        String formattedDate = "Monday, 9 Sept";
        String info = "Lymphoedema";

        CalendarEntry calendarEntry = new CalendarEntry(date, formattedDate, info);

        assertNotNull(calendarEntry);
        assertEquals(date, calendarEntry.getDate());
        assertEquals(formattedDate, calendarEntry.getFormattedDate());
        assertEquals(info, calendarEntry.getInfo());
    }

    @Test
    public void testCalendarSetters() {
        CalendarEntry calendarEntry = new CalendarEntry(null, null, null);

        Date date = new Date();
        String formattedDate = "Monday, 9 Sept";
        String info = "Lymphoedema";

        calendarEntry.setDate(date);
        calendarEntry.setFormattedDate(formattedDate);
        calendarEntry.setInfo(info);

        assertEquals(date, calendarEntry.getDate());
        assertEquals(formattedDate, calendarEntry.getFormattedDate());
        assertEquals(info, calendarEntry.getInfo());
    }
}
