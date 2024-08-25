package uk.ac.cf.spring.nhs.Diary.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import uk.ac.cf.spring.nhs.Measurement.Entity.Measurement;
import uk.ac.cf.spring.nhs.Photo.Entity.Photo;
import uk.ac.cf.spring.nhs.Symptom.Entity.Symptom;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "DiaryEntries")
@FilterDef(name = "relatedEntityTypeFilter", parameters = @ParamDef(name = "relatedEntityType", type = String.class))
public class DiaryEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "EntryID")
    private long id;

    @Column(name = "EntryDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate date;

    @Column(name = "EntryMood")
    private Mood mood;

    @OneToMany(mappedBy = "diaryEntry", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Measurement> measurements = new ArrayList<>();

    @OneToMany(mappedBy = "diaryEntry", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Symptom> symptoms = new ArrayList<>();

    @OneToMany(mappedBy = "diaryEntry", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Photo> photos = new ArrayList<>();

    @Column(name = "EntryNotes")
    private String notes;

    @Column(name = "UserID", nullable = false)
    private long userId;

    public DiaryEntry() {}

    public DiaryEntry(long userId, LocalDate date) {
        this.userId = userId;
        this.date = date;
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
