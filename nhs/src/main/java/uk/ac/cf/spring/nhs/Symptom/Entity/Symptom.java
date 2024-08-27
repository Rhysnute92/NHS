package uk.ac.cf.spring.nhs.Symptom.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uk.ac.cf.spring.nhs.Diary.Entity.DiaryEntry;
import uk.ac.cf.spring.nhs.Event.Entity.Event;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "Symptoms")
public class Symptom {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "SymptomID")
    private long id;

    @Column(name = "SymptomName", nullable = false)
    private String name;

    @Column(name = "SymptomSeverity", nullable = false)
    private int severity;

    @Column(name = "SymptomStartDate")
    private Date startDate;

    @Column(name = "SymptomIsActive")
    private Boolean isActive;

    @Column(name = "UserID")
    private long userId;

    @ManyToOne
    @JoinColumn(name = "EventID", nullable = true)
    @JsonBackReference
    private Event event;

    @ManyToOne
    @JoinColumn(name = "EntryID", nullable = true)
    @JsonBackReference
    private DiaryEntry diaryEntry;

    protected Symptom() {}

    // Constructor for standalone symptom
    public Symptom(String name, int severity, long userId) {
        this.name = name;
        this.severity = severity;
        this.userId = userId;
    }
}
