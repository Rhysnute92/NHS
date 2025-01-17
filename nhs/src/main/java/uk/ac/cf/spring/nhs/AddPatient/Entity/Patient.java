package uk.ac.cf.spring.nhs.AddPatient.Entity;

import java.time.LocalDate;
import java.util.Date;

import org.hibernate.annotations.DynamicUpdate;
import jakarta.persistence.*;
import uk.ac.cf.spring.nhs.Security.UserCredentials.UserCredentials;

@Entity
@DynamicUpdate
@Table(name = "Patients")
public class Patient {

    @Id
    @Column(name = "UserID")
    private Long userId;
    @Column(name = "PatientEmail", unique = true, nullable = false)
    private String patientEmail;
    @Column(name = "PatientMobile")
    private String patientMobile;
    @Column(name = "NHSNumber", unique = true, nullable = false)
    private String nhsNumber;
    @Column(name = "PatientDOB")
    private LocalDate patientDOB;
    @Column(name = "PatientName")
    private String patientName;
    @Column(name = "PatientLastName")
    private String patientLastName;
    @Column(name = "PatientTitle")
    private String patientTitle;
    @Column(name = "PatientClinic")
    private String patientClinic;
    @ManyToOne
    @JoinColumn(name = "UserID", insertable = false, updatable = false)
    private UserCredentials userCredentials;

    private String encryptionKey;

    // Getters and setters
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPatientEmail() {
        return patientEmail;
    }
    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }
    public String getPatientMobile() {
        return patientMobile;
    }
    public void setPatientMobile(String patientMobile) {
        this.patientMobile = patientMobile;
    }
    public String getNhsNumber() {
        return nhsNumber;
    }
    public void setNhsNumber(String nhsNumber) {
        this.nhsNumber = nhsNumber;
    }
    public LocalDate getPatientDOB() {
        return patientDOB;
    }
    public void setPatientDOB(LocalDate patientDOB) {
        this.patientDOB = patientDOB;
    }
    public String getPatientName() {
        return patientName;
    }
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
    public String getPatientLastName() {
        return patientLastName;
    }
    public void setPatientLastName(String patientLastName) {
        this.patientLastName = patientLastName;
    }
    public String getPatientTitle() {
        return patientTitle;
    }
    public void setPatientTitle(String patientTitle) {
        this.patientTitle = patientTitle;
    }
    public String getPatientClinic() {
        return patientClinic;
    }
    public void setPatientClinic(String patientClinic) {
        this.patientClinic = patientClinic;
    }
    public UserCredentials getUserCredentials() {
        return userCredentials;
    }
    public void setUserCredentials(UserCredentials userCredentials) {
        this.userCredentials = userCredentials;
    }
    public String getEncryptionKey() {
        return encryptionKey;
    }
    public void setEncryptionKey(String encryptionKey) {
        this.encryptionKey = encryptionKey;
    }
}
