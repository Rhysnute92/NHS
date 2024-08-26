package uk.ac.cf.spring.nhs.Diary.DTO;

import uk.ac.cf.spring.nhs.Diary.Entity.Mood;

import java.time.LocalDate;

public class MoodDTO {
    private Mood mood;
    private LocalDate date;

    public MoodDTO(Mood mood, LocalDate date) {
        this.mood = mood;
        this.date = date;
    }

    public Mood getMood() {
        return mood;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
