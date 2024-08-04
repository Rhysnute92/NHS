package uk.ac.cf.spring.nhs.Calendar.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Calendar")
public class Calendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer calendarID;

    @Column(name = "UserID", nullable = false)
    private Integer userID;

    private String calendarName;

    /**
     * @return the calendarID
     */
    public Integer getCalendarID() {
        return calendarID;
    }

    /**
     * @param calendarID the calendarID to set
     */
    public void setCalendarID(Integer calendarID) {
        this.calendarID = calendarID;
    }

    /**
     * @return the calendarName
     */
    public String getCalendarName() {
        return calendarName;
    }

    /**
     * @param calendarName the calendarName to set
     */
    public void setCalendarName(String calendarName) {
        this.calendarName = calendarName;
    }
}
