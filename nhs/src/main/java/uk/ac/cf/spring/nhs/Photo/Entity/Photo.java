package uk.ac.cf.spring.nhs.Photo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uk.ac.cf.spring.nhs.Diary.Entity.DiaryEntry;
import uk.ac.cf.spring.nhs.Event.Entity.Event;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "Photos")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "PhotoID")
    private long id;

    @Column(name = "PhotoURL")
    private String url;

    @Column(name = "PhotoDate")
    private Date date;

    @Column(name = "PhotoBodyPart")
    private String bodyPart;

    @Column(name = "UserID")
    private long userId;

    @ManyToOne
    @JoinColumn(name = "EventID", nullable = true)
    @JsonIgnore
    private Event event;

    @ManyToOne
    @JoinColumn(name = "EntryID", nullable = true)
    @JsonIgnore
    private DiaryEntry diaryEntry;

    protected Photo() {}

    // Constructor for standalone photo
    public Photo(String url, Date date, String bodyPart, long userId) {
        this.url = url;
        this.date = date;
        this.bodyPart = bodyPart;
        this.userId = userId;
    }
}
