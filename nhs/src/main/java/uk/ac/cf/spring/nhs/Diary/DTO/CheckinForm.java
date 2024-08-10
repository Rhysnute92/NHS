package uk.ac.cf.spring.nhs.Diary.DTO;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class CheckinForm {
    private String mood;
    private List<SymptomDTO> symptoms;
    private List<MeasurementDTO> measurements;
    private String notes;
    private List<MultipartFile> photos; // Add photos field

    public CheckinForm() {
        this.symptoms = new ArrayList<>();
        this.measurements = new ArrayList<>();
        this.photos = new ArrayList<>();
    }

    public CheckinForm(String mood, List<SymptomDTO> symptoms, List<MeasurementDTO> measurements, String notes, List<MultipartFile> photos) {
        this.mood = mood;
        this.symptoms = symptoms != null ? symptoms : new ArrayList<>();
        this.measurements = measurements != null ? measurements : new ArrayList<>();
        this.notes = notes;
        this.photos = photos != null ? photos : new ArrayList<>();
    }

    // Getters and setters...

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public List<SymptomDTO> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(List<SymptomDTO> symptoms) {
        this.symptoms = symptoms;
    }

    public List<MeasurementDTO> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<MeasurementDTO> measurements) {
        this.measurements = measurements;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<MultipartFile> getPhotos() {
        return photos;
    }

    public void setPhotos(List<MultipartFile> photos) {
        this.photos = photos;
    }

    @Override
    public String toString() {
        return "CheckinForm{" +
                "mood='" + mood + '\'' +
                ", symptoms=" + symptoms +
                ", measurements=" + measurements +
                ", notes='" + notes + '\'' +
                ", photos=" + photos +
                '}';
    }
}
