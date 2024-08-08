package uk.ac.cf.spring.nhs.Appointments.Model;

import java.time.LocalDateTime;
import java.util.Calendar;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer apptID;

    private String apptTitle;
    private LocalDateTime apptDateTime; // Combined date and time
    private String apptType;
    private String apptProvider;
    private String apptLocation;
    private String apptInfo;

    // Getters and setters

    public Integer getApptID() {
        return apptID;
    }

    public void setApptID(Integer apptID) {
        this.apptID = apptID;
    }

    public String getApptTitle() {
        return apptTitle;
    }

    public void setApptTitle(String apptTitle) {
        this.apptTitle = apptTitle;
    }

    public LocalDateTime getApptDateTime() {
        return apptDateTime;
    }

    public void setApptDateTime(LocalDateTime apptDateTime) {
        this.apptDateTime = apptDateTime;
    }

    public String getApptType() {
        return apptType;
    }

    public void setApptType(String apptType) {
        this.apptType = apptType;
    }

    public String getApptProvider() {
        return apptProvider;
    }

    public void setApptProvider(String apptProvider) {
        this.apptProvider = apptProvider;
    }

    public String getApptLocation() {
        return apptLocation;
    }

    public void setApptLocation(String apptLocation) {
        this.apptLocation = apptLocation;
    }

    public String getApptInfo() {
        return apptInfo;
    }

    public void setApptInfo(String apptInfo) {
        this.apptInfo = apptInfo;
    }
}
