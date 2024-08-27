package uk.ac.cf.spring.nhs.Photo.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uk.ac.cf.spring.nhs.Diary.Entity.DiaryEntry;
import uk.ac.cf.spring.nhs.Event.Entity.Event;

import java.time.LocalDate;
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
    private LocalDate date;

    @Column(name = "PhotoBodyPart")
    private String bodyPart;

    @Column(name = "UserID")
    private long userId;

    @ManyToOne
    @JoinColumn(name = "EntryID", nullable = true)
    @JsonBackReference
    private DiaryEntry diaryEntry;

    protected Photo() {}

    // Constructor for standalone photo
    public Photo(String url, String bodyPart, long userId, LocalDate date) {
        this.url = url;
        this.bodyPart = bodyPart;
        this.userId = userId;
        this.date = date;
    }
}
