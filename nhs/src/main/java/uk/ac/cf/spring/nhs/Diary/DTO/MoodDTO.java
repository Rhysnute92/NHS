package uk.ac.cf.spring.nhs.Diary.DTO;

import uk.ac.cf.spring.nhs.Diary.Entity.Mood;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MoodDTO {
    private Mood mood;
    private LocalDateTime date;

    public MoodDTO(Mood mood, LocalDateTime date) {
        this.mood = mood;
        this.date = date;
    }

    public Mood getMood() {
        return mood;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
