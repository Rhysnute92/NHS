package uk.ac.cf.spring.nhs.Diary.DTO;

import org.springframework.web.multipart.MultipartFile;
import uk.ac.cf.spring.nhs.Diary.Entity.Measurement;

import java.util.ArrayList;
import java.util.List;
public class CheckinForm {
    private String mood;
    private Integer troubleSleepingSeverity;
    private Integer painSeverity;
    private Integer numbnessSeverity;
    private List<String> measurementTypes;
    private List<Float> measurementValues;
    private List<String> measurementUnits;
    private String notes;
    private List<MultipartFile> photos;

    public CheckinForm() {
    }

    public CheckinForm(String mood, Integer troubleSleepingSeverity, Integer painSeverity, Integer numbnessSeverity, List<String> measurementTypes, List<Float> measurementValues, List<String> measurementUnits, String notes, List<MultipartFile> photos) {
        this.mood = mood;
        this.troubleSleepingSeverity = troubleSleepingSeverity;
        this.painSeverity = painSeverity;
        this.numbnessSeverity = numbnessSeverity;
        this.measurementTypes = measurementTypes;
        this.measurementValues = measurementValues;
        this.measurementUnits = measurementUnits;
        this.notes = notes;
        this.photos = photos;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public Integer getTroubleSleepingSeverity() {
        return troubleSleepingSeverity;
    }

    public void setTroubleSleepingSeverity(Integer troubleSleepingSeverity) {
        this.troubleSleepingSeverity = troubleSleepingSeverity;
    }

    public Integer getPainSeverity() {
        return painSeverity;
    }

    public void setPainSeverity(Integer painSeverity) {
        this.painSeverity = painSeverity;
    }

    public Integer getNumbnessSeverity() {
        return numbnessSeverity;
    }

    public void setNumbnessSeverity(Integer numbnessSeverity) {
        this.numbnessSeverity = numbnessSeverity;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Measurement> getMeasurements(int userId) {
        List<Measurement> measurements = new ArrayList<>();
        for (int i = 0; i < measurementTypes.size(); i++) {
            measurements.add(new Measurement(measurementTypes.get(i), measurementValues.get(i), measurementUnits.get(i), userId));
        }
        return measurements;
    }

    public List<String> getMeasurementTypes() {
        return measurementTypes;
    }

    public void setMeasurementTypes(List<String> measurementTypes) {
        this.measurementTypes = measurementTypes;
    }

    public List<Float> getMeasurementValues() {
        return measurementValues;
    }

    public void setMeasurementValues(List<Float> measurementValues) {
        this.measurementValues = measurementValues;
    }

    public List<String> getMeasurementUnits() {
        return measurementUnits;
    }

    public void setMeasurementUnits(List<String> measurementUnits) {
        this.measurementUnits = measurementUnits;
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
                ", troubleSleepingSeverity=" + troubleSleepingSeverity +
                ", painSeverity=" + painSeverity +
                ", numbnessSeverity=" + numbnessSeverity +
                ", notes='" + notes + '\'' +
                ", photos=" + photos +
                '}';
    }

}
