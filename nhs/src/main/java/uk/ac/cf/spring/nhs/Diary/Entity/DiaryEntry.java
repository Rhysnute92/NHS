package uk.ac.cf.spring.nhs.Diary.Entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import uk.ac.cf.spring.nhs.Measurement.Entity.Measurement;
import uk.ac.cf.spring.nhs.Photo.Entity.Photo;
import uk.ac.cf.spring.nhs.Symptom.Entity.Symptom;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "DiaryEntries")
@FilterDef(name = "relatedEntityTypeFilter", parameters = @ParamDef(name = "relatedEntityType", type = String.class))
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


    @OneToMany(mappedBy = "relatedEntityId", cascade = CascadeType.ALL, orphanRemoval = true)
    @Filter(name = "relatedEntityTypeFilter", condition = "related_entity_type = 'DiaryEntry'")
    private Set<Measurement> measurements;

    @OneToMany(mappedBy = "relatedEntityId", cascade = CascadeType.ALL, orphanRemoval = true)
    @Filter(name = "relatedEntityTypeFilter", condition = "related_entity_type = 'Event'")
    private Set<Symptom> symptoms;

    @OneToMany(mappedBy = "relatedEntityId", cascade = CascadeType.ALL, orphanRemoval = true)
    @Filter(name = "relatedEntityTypeFilter", condition = "related_entity_type = 'DiaryEntry'")
    private Set<Photo> photos;

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
