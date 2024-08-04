package uk.ac.cf.spring.nhs.Calendar.Model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import uk.ac.cf.spring.nhs.Appointments.Model.Appointments;

@Entity
@Table(name = "Calendar")
public class Calendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer calendarID;

    @Column(name = "userID", nullable = false)
    private Integer userID;

    private String calendarName;

    @OneToMany(mappedBy = "calendar", cascade = CascadeType.ALL)
    private List<Appointments> appointments;

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

    /**
     * @return the appointments
     */
    public List<Appointments> getAppointments() {
        return appointments;
    }

    /**
     * @param appointments the appointments to set
     */
    public void setAppointments(List<Appointments> appointments) {
        this.appointments = appointments;
    }

}
