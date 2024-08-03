package uk.ac.cf.spring.nhs.Diary.Entity;

public enum DiaryMood {
    GREAT, GOOD, OKAY, BAD, AWFUL;

    public static DiaryMood fromString(String mood) {
        if (mood == null || mood.isEmpty()) {
            return null;
        }
        try {
            return DiaryMood.valueOf(mood.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
