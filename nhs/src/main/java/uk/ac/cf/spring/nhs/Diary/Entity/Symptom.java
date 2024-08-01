package uk.ac.cf.spring.nhs.Diary.Entity;

import jakarta.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Symptoms")
public class Symptom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SymptomID")
    private int id;

    @Column(name = "SymptomName", nullable = false)
    private String name;

    @Column(name = "SymptomSeverity", nullable = false)
    private int severity;

    @Column(name = "SymptomStartDate", nullable = false)
    private Date startDate;

    @Column(name = "SymptomIsActive", nullable = false)
    private Boolean isActive;

    @OneToMany(mappedBy = "symptom", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DiarySymptom> diarySymptoms = new HashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Set<DiarySymptom> getDiarySymptoms() {
        return diarySymptoms;
    }

    public void setDiarySymptoms(Set<DiarySymptom> diarySymptoms) {
        this.diarySymptoms = diarySymptoms;
    }
}
