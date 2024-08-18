package uk.ac.cf.spring.nhs.Diary.Entity;

import jakarta.persistence.*;
import uk.ac.cf.spring.nhs.Measurement.Entity.Measurement;
import uk.ac.cf.spring.nhs.Photo.Entity.Photo;
import uk.ac.cf.spring.nhs.Symptom.Entity.Symptom;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "DiaryEntries")
public class DiaryEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EntryID")
    private long id;

    @Column(name = "EntryDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "EntryMood")
    private Mood mood;

    @ManyToMany
    @JoinTable(
            name = "DiaryMeasurements",
            joinColumns = @JoinColumn(name = "EntryId"),
            inverseJoinColumns = @JoinColumn(name = "MeasurementId")
    )
    private Set<Measurement> measurements = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "DiarySymptoms",
            joinColumns = @JoinColumn(name = "EntryId"),
            inverseJoinColumns = @JoinColumn(name = "SymptomId")
    )
    private Set<Symptom> symptoms = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "DiaryPhotos",
            joinColumns = @JoinColumn(name = "EntryId"),
            inverseJoinColumns = @JoinColumn(name = "PhotoId")
    )
    private Set<Photo> photos = new HashSet<>();


    @Column(name = "EntryNotes")
    private String notes;

    @Column(name = "UserID", nullable = false)
    private long userId;

    public DiaryEntry() {}

    public DiaryEntry(long userId, Date date) {
        this.userId = userId;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Mood getMood() {
        return mood;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    public Set<Symptom> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(Set<Symptom> symptoms) {
        this.symptoms = symptoms;
    }

    public Set<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(Set<Measurement> measurements) {
        this.measurements = measurements;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }


    @Override
    public String toString() {
        return "DiaryEntry{" +
                "id=" + id +
                ", date=" + date +
                ", mood=" + mood +
                ", photos=" + photos +
                ", measurements=" + measurements +
                ", symptoms=" + symptoms +
                ", notes='" + notes + '\'' +
                ", userId=" + userId +
                '}';
    }
}
