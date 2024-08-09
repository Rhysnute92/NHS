package uk.ac.cf.spring.nhs.Diary.Entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Photos")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PhotoID")
    private int id;

    @Column(name = "PhotoURL")
    private String url;

    @Column(name = "PhotoDate")
    private Date date;

    @Column(name = "PhotoBodyPart")
    private String bodyPart;

    @Column(name = "UserID")
    private long userId;

    public Photo() {}

    public Photo(String url, Date date, String bodyPart, long userId) {
        this.url = url;
        this.date = date;
        this.bodyPart = bodyPart;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
