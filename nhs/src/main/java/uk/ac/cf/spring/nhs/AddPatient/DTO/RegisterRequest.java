package uk.ac.cf.spring.nhs.AddPatient.DTO;

import java.time.LocalDate;

public class RegisterRequest {
    private String patientName;
    private String patientLastName;
    private String patientEmail;
    private String patientMobile;
    private String nhsNumber;
    private LocalDate patientDOB;
    private String patientTitle;
    private String patientClinic;

    // Getters and Setters
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
}
