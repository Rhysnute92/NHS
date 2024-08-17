package uk.ac.cf.spring.nhs.Treatment.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Treatments")
public class Treatment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TreatmentID")
    private Long id;

    @Column(name = "TreatmentType")
    private String type;

    @Column(name = "TreatmentDetails")
    private String details;

    @Column(name = "UserID")
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

    public String getTreatmentType() {
        return type;
    }

    public void setTreatmentType(String type) {
        this.type = type;
    }
}
