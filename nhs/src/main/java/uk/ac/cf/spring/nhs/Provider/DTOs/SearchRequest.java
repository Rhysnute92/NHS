package uk.ac.cf.spring.nhs.Provider.DTOs;

public class SearchRequest {
    private String patientName;
    private String patientLastName;
    private String patientNhsNumber;
    private String patientDOB;

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
    public String getPatientDOB() {
        return patientDOB;
    }
    public void setPatientDOB(String patientDOB) {
        this.patientDOB = patientDOB;
    }
    public String getPatientNhsNumber() {
        return patientNhsNumber;
    }public void setPatientNhsNumber(String patientNhsNumber) {
        this.patientNhsNumber = patientNhsNumber;
    }
}
