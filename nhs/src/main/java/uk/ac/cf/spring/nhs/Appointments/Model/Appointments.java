package uk.ac.cf.spring.nhs.Appointments.Model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import uk.ac.cf.spring.nhs.Calendar.Model.Calendar;

@Entity
@Table(name = "Appointments")
public class Appointments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer apptID;

    @ManyToOne
    @JoinColumn(name = "calendarID", nullable = false)
    private Calendar calendar;

    private String apptTitle;
    private LocalDateTime apptStartTime;
    private LocalDateTime apptEndTime;
    private String apptType;
    private String apptProvider;
    private String apptLocation;
    private String apptInfo;

    /**
     * @return the apptID
     */
    public Integer getApptID() {
        return apptID;
    }

    /**
     * @param apptID the apptID to set
     */
    public void setApptID(Integer apptID) {
        this.apptID = apptID;
    }

    /**
     * @return the calendar
     */
    public Calendar getCalendar() {
        return calendar;
    }

    /**
     * @param calendar the calendar to set
     */
    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    /**
     * @return the apptTitle
     */
    public String getApptTitle() {
        return apptTitle;
    }

    /**
     * @param apptTitle the apptTitle to set
     */
    public void setApptTitle(String apptTitle) {
        this.apptTitle = apptTitle;
    }

    /**
     * @return the apptStartTime
     */
    public LocalDateTime getApptStartTime() {
        return apptStartTime;
    }

    /**
     * @param apptStartTime the apptStartTime to set
     */
    public void setApptStartTime(LocalDateTime apptStartTime) {
        this.apptStartTime = apptStartTime;
    }

    /**
     * @return the apptEndTime
     */
    public LocalDateTime getApptEndTime() {
        return apptEndTime;
    }

    /**
     * @param apptEndTime the apptEndTime to set
     */
    public void setApptEndTime(LocalDateTime apptEndTime) {
        this.apptEndTime = apptEndTime;
    }

    /**
     * @return the apptType
     */
    public String getApptType() {
        return apptType;
    }

    /**
     * @param apptType the apptType to set
     */
    public void setApptType(String apptType) {
        this.apptType = apptType;
    }

    /**
     * @return the apptProvider
     */
    public String getApptProvider() {
        return apptProvider;
    }

    /**
     * @param apptProvider the apptProvider to set
     */
    public void setApptProvider(String apptProvider) {
        this.apptProvider = apptProvider;
    }

    /**
     * @return the apptLocation
     */
    public String getApptLocation() {
        return apptLocation;
    }

    /**
     * @param apptLocation the apptLocation to set
     */
    public void setApptLocation(String apptLocation) {
        this.apptLocation = apptLocation;
    }

    /**
     * @return the apptInfo
     */
    public String getApptInfo() {
        return apptInfo;
    }

    /**
     * @param apptInfo the apptInfo to set
     */
    public void setApptInfo(String apptInfo) {
        this.apptInfo = apptInfo;
    }

}
