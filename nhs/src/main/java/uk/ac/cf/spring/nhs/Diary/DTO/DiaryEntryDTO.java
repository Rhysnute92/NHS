package uk.ac.cf.spring.nhs.Diary.DTO;

import lombok.Getter;
import lombok.Setter;
import uk.ac.cf.spring.nhs.Diary.Entity.DiaryEntry;
import uk.ac.cf.spring.nhs.Diary.Entity.Mood;
import uk.ac.cf.spring.nhs.Measurement.Entity.Measurement;
import uk.ac.cf.spring.nhs.Photo.Entity.Photo;
import uk.ac.cf.spring.nhs.Symptom.Entity.Symptom;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class DiaryEntryDTO {
    private LocalDate date;
    private Mood mood;
    private String notes;

    private List<Measurement> measurements;
    private List<Symptom> symptoms;
    private List<Photo> photos;
    private Map<String, Map<String, Measurement>> measurementsGroupedByLocation;



    public DiaryEntryDTO(LocalDate date, Map<String, Map<String, Measurement>> measurementsGroupedByLocation, Mood mood, String notes, List<Measurement> measurements, List<Symptom> symptoms, List<Photo> photos) {
        this.date = date;
        this.measurementsGroupedByLocation = measurementsGroupedByLocation;
        this.mood = mood;
        this.notes = notes;
        this.measurements = measurements;
        this.symptoms = symptoms;
        this.photos = photos;
    }

    public Map<String, Map<String, Measurement>> getMeasurementsGroupedByLocation() {
        return measurementsGroupedByLocation;
    }

    public void setMeasurementsGroupedByLocation(Map<String, Map<String, Measurement>> measurementsGroupedByLocation) {
        this.measurementsGroupedByLocation = measurementsGroupedByLocation;
    }
}
