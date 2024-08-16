package uk.ac.cf.spring.nhs.Symptom.DTO;

public class SymptomDTO {
    private String name;
    private Integer severity;

    public SymptomDTO() {
    }

    public SymptomDTO(String name, Integer severity) {
        this.name = name;
        this.severity = severity;
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

    @Override
    public String toString() {
        return "SymptomDTO{" +
                "name='" + name + '\'' +
                ", severity=" + severity +
                '}';
    }
}
