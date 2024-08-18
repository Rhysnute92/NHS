package uk.ac.cf.spring.nhs.Event.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import uk.ac.cf.spring.nhs.Symptom.Entity.Symptom;
import uk.ac.cf.spring.nhs.Treatment.Entity.Treatment;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "EventID")
    private Long id;

    @Column(name = "EventDate", nullable = false)
    private LocalDate date;

    @Column(name = "EventDuration")
    private Integer duration;

    @Column(name = "UserID")
    private long userId;

    @OneToMany(mappedBy = "relatedEntityId", cascade = CascadeType.ALL, orphanRemoval = true)
    @Filter(name = "relatedEntityTypeFilter", condition = "RelatedEntityType = 'Event'")
    private Set<Symptom> symptoms;

    @OneToMany(mappedBy = "relatedEntityId", cascade = CascadeType.ALL, orphanRemoval = true)
    @Filter(name = "relatedEntityTypeFilter", condition = "RelatedEntityType = 'Event'")
    private Set<Treatment> treatments;

    protected Event() {}

    public Event(LocalDate date, Integer duration, long userId) {
        this.date = date;
        this.duration = duration;
        this.userId = userId;
    }
}
