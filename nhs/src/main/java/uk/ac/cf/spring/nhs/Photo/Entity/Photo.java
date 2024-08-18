package uk.ac.cf.spring.nhs.Photo.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @Column(name = "RelatedEntityType")
    private String relatedEntityType;

    @Column(name = "RelatedEntityId")
    private Long relatedEntityId;

    protected Photo() {}

    // Constructor for standalone photo
    public Photo(String url, Date date, String bodyPart, long userId) {
        this.url = url;
        this.date = date;
        this.bodyPart = bodyPart;
        this.userId = userId;
    }

    // Constructor for linked photo
    public Photo(String url, Date date, String bodyPart, long userId, Long relatedEntityId, String relatedEntityType) {
        this.url = url;
        this.date = date;
        this.bodyPart = bodyPart;
        this.userId = userId;
        this.relatedEntityId = relatedEntityId;
        this.relatedEntityType = relatedEntityType;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", date=" + date +
                ", bodyPart='" + bodyPart + '\'' +
                ", userId=" + userId +
                '}';
    }
}
