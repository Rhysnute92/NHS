package uk.ac.cf.spring.nhs.Calendar.Entity;

import org.junit.jupiter.api.Test;

import uk.ac.cf.spring.nhs.Appointments.Model.Appointments;

import java.lang.reflect.Type;
import java.sql.Time;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class CalendarEntryTest {

    @Test
    public void testCalendarGetters() {
        String time = "15:15";
        String type = "Lymphoedema";
        String info = "Lymphoedema";

        Appointments calendarEntry = new Appointments();

        assertNotNull(calendarEntry);
        assertEquals(time, calendarEntry.getApptStartTime());
        assertEquals(type, calendarEntry.getApptType());
        assertEquals(info, calendarEntry.getApptInfo());
    }

    @Test
    public void testCalendarSetters() {
        Appointments calendarEntry = new Appointments();

        String type = "Lymphoedema";
        String info = "Lymphoedema";

        calendarEntry.setApptType(type);
        calendarEntry.setApptInfo(info);

        assertEquals(type, calendarEntry.getApptType());
        assertEquals(info, calendarEntry.getApptInfo());
    }
}
