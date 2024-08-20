package uk.ac.cf.spring.nhs.Measurement.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import uk.ac.cf.spring.nhs.Diary.Entity.DiaryEntry;
import uk.ac.cf.spring.nhs.Event.Entity.Event;

import java.time.LocalDate;

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

    @Column(name = "MeasurementDate")
    private LocalDate date;

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
    public Measurement(String type, float value, String unit, long userId, LocalDate date) {
        this.type = type;
        this.value = value;
        this.unit = unit;
        this.userId = userId;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", value=" + value +
                ", unit='" + unit + '\'' +
                ", date=" + date +
                ", userId=" + userId +
                ", event=" + event +
                ", diaryEntry=" + diaryEntry +
                '}';
    }
}
