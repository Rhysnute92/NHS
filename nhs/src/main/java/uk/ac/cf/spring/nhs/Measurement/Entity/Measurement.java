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

    @Column(name = "MeasurementLocation")
    private String location;

    @Column(name = "MeasurementDate")
    private LocalDate date;

    @Column(name = "MeasurementSide")
    private String side;

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
    public Measurement(String type, float value, String unit, long userId, LocalDate date, String location, String side) {
        this.type = type;
        this.value = value;
        this.unit = unit;
        this.userId = userId;
        this.date = date;
        this.location = location;
        this.side = side;
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", value=" + value +
                ", unit='" + unit + '\'' +
                ", location='" + location + '\'' +
                ", date=" + date +
                ", side='" + side + '\'' +
                ", userId=" + userId +
                '}';
    }
}
