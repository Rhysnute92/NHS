package uk.ac.cf.spring.nhs.Treatment.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Treatments")
public class Treatment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TreatmentID")
    private Long id;

    @Column(name = "TreatmentType", nullable = false)
    private String type;

    @Column(name = "TreatmentDetails")
    private String details;

    @Column(name = "UserID", nullable = false)
    private long userId;

    @Column(name = "related_entity_type")
    private String relatedEntityType;

    @Column(name = "related_entity_id")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getRelatedEntityType() {
        return relatedEntityType;
    }

    public void setRelatedEntityType(String relatedEntityType) {
        this.relatedEntityType = relatedEntityType;
    }

    public Long getRelatedEntityId() {
        return relatedEntityId;
    }

    public void setRelatedEntityId(Long relatedEntityId) {
        this.relatedEntityId = relatedEntityId;
    }
}
