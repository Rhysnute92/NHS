package uk.ac.cf.spring.nhs.Diary.DTO;

import lombok.Getter;
import lombok.Setter;
import uk.ac.cf.spring.nhs.Diary.Entity.DiaryEntry;
import uk.ac.cf.spring.nhs.Diary.Entity.Mood;
import uk.ac.cf.spring.nhs.Measurement.DTO.MeasurementGroupDTO;
import uk.ac.cf.spring.nhs.Measurement.Entity.Measurement;
import uk.ac.cf.spring.nhs.Photo.Entity.Photo;
import uk.ac.cf.spring.nhs.Symptom.Entity.Symptom;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class DiaryEntryDTO {
    private LocalDateTime date;
    private List<MeasurementGroupDTO> twoSidedMeasurementGroups;
    private List<Measurement> nonSidedMeasurements;
    private Mood mood;
    private String notes;
    private List<Symptom> symptoms;
    private List<Photo> photos;
    private Long id;

    public DiaryEntryDTO() {
    }

    public DiaryEntryDTO(LocalDateTime date, List<MeasurementGroupDTO> twoSidedMeasurementGroups, List<Measurement> nonSidedMeasurements, Mood mood, String notes, List<Symptom> symptoms, List<Photo> photos, Long id) {
        this.date = date;
        this.twoSidedMeasurementGroups = twoSidedMeasurementGroups;
        this.nonSidedMeasurements = nonSidedMeasurements;
        this.mood = mood;
        this.notes = notes;
        this.symptoms = symptoms;
        this.photos = photos;
        this.id = id;
    }
}
