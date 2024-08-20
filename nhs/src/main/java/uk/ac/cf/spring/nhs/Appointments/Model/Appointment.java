package uk.ac.cf.spring.nhs.Appointments.Model;

import java.time.LocalDateTime;
import java.util.Calendar;

import groovyjarjarantlr4.v4.analysis.AnalysisPipeline;
import jakarta.persistence.*;
import uk.ac.cf.spring.nhs.AddPatient.Entity.Patient;

@Entity
@Table(name = "Appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer apptID;
    private LocalDateTime apptDateTime; // Combined date and time
    private String apptType;
    private String apptProvider;
    private String apptLocation;
    private String apptInfo;

    private Boolean isDeletable = true;
    @Column(name = "UserID", nullable = false)
    private Long userID;
    
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    // Getters and setters

    public Integer getApptID() {
        return apptID;
    }

    public void setApptID(Integer apptID) {
        this.apptID = apptID;
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

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Boolean getIsDeletable(){return isDeletable;}

    public void setIsDeletable(Boolean isDeletable){this.isDeletable = isDeletable;}

    public void setPatient(Patient patient) {
    }

    public Appointment orElse(Object o) {
        return null;
    }

    public Patient getPatient() {
        return this.patient;
    }
}
