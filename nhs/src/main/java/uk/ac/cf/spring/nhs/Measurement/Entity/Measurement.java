package uk.ac.cf.spring.nhs.Measurement.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Filter;

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

    @Column(name = "RelatedEntityType")
    private String relatedEntityType;

    @Column(name = "RelatedEntityId")
    private Long relatedEntityId;

    protected Measurement() {}

    // Constructor for standalone measurement
    public Measurement(String type, float value, String unit, long userId) {
        this.type = type;
        this.value = value;
        this.unit = unit;
        this.userId = userId;
    }

    // Constructor for linked measurement
    public Measurement(String type, float value, String unit, long userId, Long relatedEntityId, String relatedEntityType) {
        this.type = type;
        this.value = value;
        this.unit = unit;
        this.userId = userId;
        this.relatedEntityId = relatedEntityId;
        this.relatedEntityType = relatedEntityType;
    }
}
