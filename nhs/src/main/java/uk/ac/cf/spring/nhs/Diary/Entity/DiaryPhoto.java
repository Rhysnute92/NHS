package uk.ac.cf.spring.nhs.Diary.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "DiaryPhotos")
public class DiaryPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DiaryPhotoID")
    private int id;

    @ManyToOne
    @JoinColumn(name = "EntryID", nullable = false)
    private DiaryEntry diaryEntry;

    @ManyToOne
    @JoinColumn(name = "PhotoID", nullable = false)
    private Photo photo;

    public DiaryPhoto() {}

    public DiaryPhoto(DiaryEntry diaryEntry, Photo photo) {
        this.diaryEntry = diaryEntry;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DiaryEntry getDiaryEntry() {
        return diaryEntry;
    }

    public void setDiaryEntry(DiaryEntry diaryEntry) {
        this.diaryEntry = diaryEntry;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }
}
