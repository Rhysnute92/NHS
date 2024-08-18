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


    protected Treatment() {}

    public Treatment(String type, String details, long userId) {
        this.type = type;
        this.details = details;
        this.userId = userId;
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
}
