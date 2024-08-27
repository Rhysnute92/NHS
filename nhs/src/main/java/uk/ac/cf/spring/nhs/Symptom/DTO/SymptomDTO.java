package uk.ac.cf.spring.nhs.Symptom.DTO;

import java.time.LocalDate;

public class SymptomDTO {
    private String name;
    private Integer severity;
    private LocalDate date;

    public SymptomDTO() {
    }

    public SymptomDTO(String name, Integer severity, LocalDate date) {
        this.name = name;
        this.severity = severity;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSeverity() {
        return severity;
    }

    public void setSeverity(Integer severity) {
        this.severity = severity;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "SymptomDTO{" +
                "name='" + name + '\'' +
                ", severity=" + severity +
                '}';
    }
}
