package uk.ac.cf.spring.nhs.Treatment.DTO;

public class TreatmentDTO {

    private String type;
    private String details; // Optional details about the treatment (e.g. name of antibiotics)

    public TreatmentDTO() {}

    public TreatmentDTO(String type, String details) {
        this.type = type;
        this.details = details;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
