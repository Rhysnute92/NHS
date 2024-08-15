package uk.ac.cf.spring.nhs.Account;

import java.time.LocalDate;

public class PatientProfileDTO {
    private String fullName;
    private String email;
    private String mobile;
    private String nhsNumber;
    private LocalDate dateOB;
    private int age;
    private String clinic;

    public PatientProfileDTO(String fullname, String emailAddress, String mob, String nhs, LocalDate dob, int patientAge, String patientClininc){
        this.fullName = fullname;
        this.email = emailAddress;
        this.mobile = mob;
        this.nhsNumber = nhs;
        this.dateOB = dob;
        this.age = patientAge;
        this.clinic = patientClininc;
    }

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getNhsNumber() {
        return nhsNumber;
    }
    public void setNhsNumber(String nhsNumber) {
        this.nhsNumber = nhsNumber;
    }
    public LocalDate getDateOB() {
        return dateOB;
    }
    public void setDateOB(LocalDate dOB) {
        dateOB = dOB;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getClinic() {
        return clinic;
    }
    public void setClinic(String clinic) {
        this.clinic = clinic;
    }
}
