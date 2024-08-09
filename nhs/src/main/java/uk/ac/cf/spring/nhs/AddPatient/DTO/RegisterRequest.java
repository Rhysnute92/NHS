package uk.ac.cf.spring.nhs.AddPatient.DTO;

import jakarta.validation.constraints.NotEmpty;

public class RegisterRequest {

    @NotEmpty
    private String patientEmail;
    @NotEmpty
    private String patientMobile;
    @NotEmpty
    private String nhsNumber;
    @NotEmpty
    private String patientDOB;
    @NotEmpty
    private String patientName;
    @NotEmpty
    private String patientLastName;
    @NotEmpty
    private String patientTitle;

    // Getters and setters
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

    public String getPatientDOB() {
        return patientDOB;
    }

    public void setPatientDOB(String patientDOB) {
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
}
