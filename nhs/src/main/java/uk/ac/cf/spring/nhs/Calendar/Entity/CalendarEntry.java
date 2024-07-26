package uk.ac.cf.spring.nhs.Calendar.Entity;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Setter
@Getter
public class CalendarEntry {
    private Date date;
    private String formattedDate;
    private String info;

    public CalendarEntry(Date date, String formattedDate, String info) {
        this.date = date;
        this.formattedDate = formattedDate;
        this.info = info;
    }
}

