package uk.ac.cf.spring.nhs.Diary.Entity;

import jakarta.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "DiaryEntries")
public class DiaryEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EntryID")
    private int id;

    @Column(name = "EntryDate", nullable = false)
    private Date date;

    @Enumerated(EnumType.STRING)
    @Column(name = "EntryType", nullable = false)
    private DiaryEntryType type;

    @OneToMany(mappedBy = "diaryEntry", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DiaryPhoto> photos = new HashSet<>();

    @OneToMany(mappedBy = "diaryEntry", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DiaryMeasurement> measurements = new HashSet<>();

    @OneToMany(mappedBy = "diaryEntry", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DiarySymptom> symptoms = new HashSet<>();

    @Column(name = "EntryNotes", nullable = false)
    private String notes;

    @Column(name = "UserID", nullable = false)
    private int userId;

    protected DiaryEntry() {}

    public DiaryEntry(int userId, Date date, DiaryEntryType type, String notes) {
        this.userId = userId;
        this.date = date;
        this.type = type;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public DiaryEntryType getType() {
        return type;
    }

    public void setType(DiaryEntryType type) {
        this.type = type;
    }

    public Set<DiaryPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<DiaryPhoto> photos) {
        this.photos = photos;
    }

    public Set<DiaryMeasurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(Set<DiaryMeasurement> measurements) {
        this.measurements = measurements;
    }

    public Set<DiarySymptom> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(Set<DiarySymptom> symptoms) {
        this.symptoms = symptoms;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return String.format("DiaryEntry[id=%d, date='%s']", id, date);
    }
}
