package uk.ac.cf.spring.nhs.Diary.Entity;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DiaryEntryTest {

    @Test
    public void testDiaryEntryConstructorAndGetters() {
        Date date = new Date();
        String formattedDate = "Wednesday, 20 July";
        String content = "Content for entry";

        DiaryEntry diaryEntry = new DiaryEntry(date, formattedDate, content);

        assertNotNull(diaryEntry);
        assertEquals(date, diaryEntry.getDate());
        assertEquals(formattedDate, diaryEntry.getFormattedDate());
        assertEquals(content, diaryEntry.getContent());
    }

    @Test
    public void testDiaryEntrySetters() {
        DiaryEntry diaryEntry = new DiaryEntry(null, null, null);

        Date date = new Date();
        String formattedDate = "Wednesday, 20 July";
        String content = "Updated content for entry";

        diaryEntry.setDate(date);
        diaryEntry.setFormattedDate(formattedDate);
        diaryEntry.setContent(content);

        assertEquals(date, diaryEntry.getDate());
        assertEquals(formattedDate, diaryEntry.getFormattedDate());
        assertEquals(content, diaryEntry.getContent());
    }
}
