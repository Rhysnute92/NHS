package uk.ac.cf.spring.nhs.Measurement.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import uk.ac.cf.spring.nhs.Diary.Entity.DiaryEntry;
import uk.ac.cf.spring.nhs.Event.Entity.Event;

@Getter
@Setter
@Entity
@Table(name = "Measurements")
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "MeasurementID")
    private long id;

    @Column(name = "MeasurementType")
    private String type;

    @Column(name = "MeasurementValue")
    private float value;

    @Column(name = "MeasurementUnit")
    private String unit;

    @Column(name = "UserID")
    private long userId;

    @ManyToOne
    @JoinColumn(name = "EventID", nullable = true)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "EntryID", nullable = true)
    private DiaryEntry diaryEntry;

    protected Measurement() {}

    // Constructor for standalone measurement
    public Measurement(String type, float value, String unit, long userId) {
        this.type = type;
        this.value = value;
        this.unit = unit;
        this.userId = userId;
    }
}
