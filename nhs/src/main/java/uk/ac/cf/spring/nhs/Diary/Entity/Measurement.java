package uk.ac.cf.spring.nhs.Diary.Entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "Measurements")
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MeasurementID")
    private long id;

    @ManyToMany(mappedBy = "measurements")
    private Set<DiaryEntry> diaryEntries;

    @Column(name = "MeasurementType")
    private String type;

    @Column(name = "MeasurementValue")
    private float value;

    @Column(name = "MeasurementUnit")
    private String unit;

    @Column(name = "UserID")
    private long userId;

    public Measurement() {}

    public Measurement(String type, float value, String unit, long userId) {
        this.type = type;
        this.value = value;
        this.unit = unit;
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
