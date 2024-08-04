package uk.ac.cf.spring.nhs.Calendar.Service;

import org.springframework.stereotype.Service;

import uk.ac.cf.spring.nhs.CalendarEntry.Model.CalendarEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class CalendarService {

    public CalendarService() {
    }

    public List<CalendarEntry> getCalendarEntries() {
        List<CalendarEntry> calendarEntries = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, dd MMMM");

        for (int i = 0; i < 7; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -i);
            Date date = calendar.getTime();
            String formattedDate = formatter.format(date);
            calendarEntries.add(new CalendarEntry(date, formattedDate, "Test" + (i + 1)));
        }
        return calendarEntries;
    }
}

