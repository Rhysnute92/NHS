package uk.ac.cf.spring.nhs.Treatment.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uk.ac.cf.spring.nhs.Diary.Entity.DiaryEntry;
import uk.ac.cf.spring.nhs.Event.Entity.Event;

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

    @ManyToOne
    @JoinColumn(name = "EventID", nullable = true)
    @JsonIgnore
    private Event event;

    protected Treatment() {}

    public Treatment(String type, String details, long userId) {
        this.type = type;
        this.details = details;
        this.userId = userId;
    }
}
