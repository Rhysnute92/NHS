package uk.ac.cf.spring.nhs.Diary.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "DiarySymptoms")
public class DiarySymptom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DiarySymptomID")
    private int id;

    @ManyToOne
    @JoinColumn(name = "EntryID", nullable = false)
    private DiaryEntry diaryEntry;

    @ManyToOne
    @JoinColumn(name = "SymptomID", nullable = false)
    private Symptom symptom;

    protected DiarySymptom() {}

    public DiarySymptom(DiaryEntry diaryEntry, Symptom symptom) {
        this.diaryEntry = diaryEntry;
        this.symptom = symptom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DiaryEntry getDiaryEntry() {
        return diaryEntry;
    }

    public void setDiaryEntry(DiaryEntry diaryEntry) {
        this.diaryEntry = diaryEntry;
    }

    public Symptom getSymptom() {
        return symptom;
    }

    public void setSymptom(Symptom symptom) {
        this.symptom = symptom;
    }
}
