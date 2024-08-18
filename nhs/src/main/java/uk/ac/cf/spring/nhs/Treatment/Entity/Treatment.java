package uk.ac.cf.spring.nhs.Treatment.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Treatments")
public class Treatment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "TreatmentID")
    private Long id;

    @Column(name = "TreatmentType", nullable = false)
    private String type;

    @Column(name = "TreatmentDetails")
    private String details;

    @Column(name = "UserID", nullable = false)
    private long userId;

    @Column(name = "RelatedEntityType")
    private String relatedEntityType;

    @Column(name = "RelatedEntityId")
    private Long relatedEntityId;

    protected Treatment() {}

    public Treatment(String type, String details, long userId) {
        this.type = type;
        this.details = details;
        this.userId = userId;
    }

    public Treatment(String type, String details, long userId, Long relatedEntityId, String relatedEntityType) {
        this.type = type;
        this.details = details;
        this.userId = userId;
        this.relatedEntityId = relatedEntityId;
        this.relatedEntityType = relatedEntityType;
    }
}
