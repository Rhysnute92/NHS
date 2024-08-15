package uk.ac.cf.spring.nhs.Diary.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "EventDate")
    private LocalDate eventDate;

    @Column(name = "EventSymptoms")
    private String symptoms;

    @Column(name = "EventSeverity")
    private String severity;

    @Column(name = "EventDuration")
    private Integer duration;

    @Column(name = "EventTreatment")
    private String treatment;

    @Column(name = "UserID")
    private long userId;

    protected Event() {}

    public Event(LocalDate eventDate, String symptoms, String severity, Integer duration, String treatment) {
        this.eventDate = eventDate;
        this.symptoms = symptoms;
        this.severity = severity;
        this.duration = duration;
        this.treatment = treatment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}