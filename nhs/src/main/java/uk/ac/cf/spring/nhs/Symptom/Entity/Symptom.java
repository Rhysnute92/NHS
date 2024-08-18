package uk.ac.cf.spring.nhs.Symptom.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @Column(name = "RelatedEntityType")
    private String relatedEntityType;

    @Column(name = "RelatedEntityId")
    private Long relatedEntityId;

    protected Symptom() {}

    // Constructor for standalone symptom
    public Symptom(String name, int severity, long userId) {
        this.name = name;
        this.severity = severity;
        this.userId = userId;
    }

    // Constructor for linked symptom
    public Symptom(String name, int severity, long userId, Long relatedEntityId, String relatedEntityType) {
        this.name = name;
        this.severity = severity;
        this.userId = userId;
        this.relatedEntityId = relatedEntityId;
        this.relatedEntityType = relatedEntityType;
    }
}
