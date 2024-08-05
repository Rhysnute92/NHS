package uk.ac.cf.spring.nhs.Diary.DTO;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class CheckinForm {
    private String mood;
    private Integer troubleSleepingSeverity;
    private Integer painSeverity;
    private Integer numbnessSeverity;
    private String notes;
    private List<MultipartFile> photos;

    public CheckinForm() {
    }

    public CheckinForm(String mood, Integer troubleSleepingSeverity, Integer painSeverity, Integer numbnessSeverity, String notes, List<MultipartFile> photos) {
        this.mood = mood;
        this.troubleSleepingSeverity = troubleSleepingSeverity;
        this.painSeverity = painSeverity;
        this.numbnessSeverity = numbnessSeverity;
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

    public List<MultipartFile> getPhotos() {
        return photos;
    }

    public void setPhotos(List<MultipartFile> photos) {
        this.photos = photos;
    }

}
