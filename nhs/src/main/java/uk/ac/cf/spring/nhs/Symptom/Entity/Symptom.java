package uk.ac.cf.spring.nhs.Symptom.Entity;

import jakarta.persistence.*;
import uk.ac.cf.spring.nhs.Diary.Entity.DiaryEntry;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Symptoms")
public class Symptom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToMany(mappedBy = "symptoms")
    private Set<DiaryEntry> diaryEntries;

    public Symptom() {}

    public Symptom(String name, int severity, long userId) {
        this.name = name;
        this.severity = severity;
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeverity() {
        return severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
