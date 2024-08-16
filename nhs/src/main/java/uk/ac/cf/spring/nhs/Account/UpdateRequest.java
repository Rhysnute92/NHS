package uk.ac.cf.spring.nhs.Account;

public class UpdateRequest {
    private String patientName;
    private String patientLastName;
    private String patientEmail;
    private String patientMobile;
    private String patientTitle;

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
    public String getPatientTitle() {
        return patientTitle;
    }
    public void setPatientTitle(String patientTitle) {
        this.patientTitle = patientTitle;
    }
}
