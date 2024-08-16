package uk.ac.cf.spring.nhs.Diary.Entity;

public enum Mood {
    GREAT, GOOD, OKAY, BAD, AWFUL;

    public static Mood fromString(String mood) {
        if (mood == null || mood.isEmpty()) {
            return null;
        }
        try {
            return Mood.valueOf(mood.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
