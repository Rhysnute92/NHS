package uk.ac.cf.spring.nhs.Photo.Entity;

import jakarta.persistence.*;
import java.util.Date;
import org.hibernate.annotations.Filter;

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

    @Column(name = "related_entity_type")
    private String relatedEntityType;

    @Column(name = "related_entity_id")
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getRelatedEntityType() {
        return relatedEntityType;
    }

    public void setRelatedEntityType(String relatedEntityType) {
        this.relatedEntityType = relatedEntityType;
    }

    public Long getRelatedEntityId() {
        return relatedEntityId;
    }

    public void setRelatedEntityId(Long relatedEntityId) {
        this.relatedEntityId = relatedEntityId;
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
