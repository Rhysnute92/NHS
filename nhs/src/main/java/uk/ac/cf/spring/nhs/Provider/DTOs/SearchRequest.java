package uk.ac.cf.spring.nhs.Provider.DTOs;

import java.time.LocalDate;

public class SearchRequest {
    private String patientName;
    private String patientLastName;
    private String patientNhsNumber;
    private LocalDate patientDOB;
    private String patientEmail;

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
    public LocalDate getPatientDOB() {
        return patientDOB;
    }
    public void setPatientDOB(LocalDate patientDOB) {
        this.patientDOB = patientDOB;
    }
    public String getPatientNhsNumber() {
        return patientNhsNumber;
    }public void setPatientNhsNumber(String patientNhsNumber) {
        this.patientNhsNumber = patientNhsNumber;
    }
    public String getPatientEmail() {
        return patientEmail;
    }
    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }
}
