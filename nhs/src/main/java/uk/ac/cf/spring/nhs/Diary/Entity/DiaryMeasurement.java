package uk.ac.cf.spring.nhs.Diary.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "DiaryMeasurements")
public class DiaryMeasurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DiaryMeasurementID")
    private int id;

    @ManyToOne
    @JoinColumn(name = "EntryID", nullable = false)
    private DiaryEntry diaryEntry;

    @ManyToOne
    @JoinColumn(name = "MeasurementID", nullable = false)
    private Measurement measurement;

    protected DiaryMeasurement() {}

    public DiaryMeasurement(DiaryEntry diaryEntry, Measurement measurement) {
        this.diaryEntry = diaryEntry;
        this.measurement = measurement;
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

    public Measurement getMeasurement() {
        return measurement;
    }

    public void setMeasurement(Measurement measurement) {
        this.measurement = measurement;
    }
}
