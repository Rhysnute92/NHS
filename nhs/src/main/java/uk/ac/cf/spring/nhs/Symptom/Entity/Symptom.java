package uk.ac.cf.spring.nhs.Symptom.Entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Symptoms")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Symptom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "related_entity_id")
    private Long relatedEntityId;

    @Column(name = "related_entity_type")
    private String relatedEntityType;

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

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeverity() {
        return severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Long getRelatedEntityId() {
        return relatedEntityId;
    }

    public void setRelatedEntityId(Long relatedEntityId) {
        this.relatedEntityId = relatedEntityId;
    }

    public String getRelatedEntityType() {
        return relatedEntityType;
    }

    public void setRelatedEntityType(String relatedEntityType) {
        this.relatedEntityType = relatedEntityType;
    }
}
