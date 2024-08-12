package uk.ac.cf.spring.nhs.Photo.Entity;

import jakarta.persistence.*;
import uk.ac.cf.spring.nhs.Diary.Entity.DiaryEntry;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "Photos")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToMany(mappedBy = "photos")
    private Set<DiaryEntry> diaryEntries;

    public Photo() {}

    public Photo(String url, Date date, String bodyPart, long userId) {
        this.url = url;
        this.date = date;
        this.bodyPart = bodyPart;
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(String bodyPart) {
        this.bodyPart = bodyPart;
    }
}
