package uk.ac.cf.spring.nhs.Event.DTO;

import uk.ac.cf.spring.nhs.Symptom.DTO.SymptomDTO;
import uk.ac.cf.spring.nhs.Treatment.DTO.TreatmentDTO;

import java.time.LocalDate;
import java.util.List;

public class EventDTO {

    private LocalDate date;
    private Integer duration;
    private Long userId;
    private List<SymptomDTO> symptoms; // List of SymptomDTO objects
    private List<TreatmentDTO> treatments; // List of TreatmentDTO objects

    public EventDTO() {}

    public EventDTO(LocalDate date, Integer duration, Long userId, List<SymptomDTO> symptoms, List<TreatmentDTO> treatments) {
        this.date = date;
        this.duration = duration;
        this.userId = userId;
        this.symptoms = symptoms;
        this.treatments = treatments;
    }

    // Getters and setters
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<SymptomDTO> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(List<SymptomDTO> symptoms) {
        this.symptoms = symptoms;
    }

    public List<TreatmentDTO> getTreatments() {
        return treatments;
    }

    public void setTreatments(List<TreatmentDTO> treatments) {
        this.treatments = treatments;
    }
}
