package uk.ac.cf.spring.nhs.Diary.Entity;

import org.junit.Before;
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

        int userId = 1;

        DiaryEntry diaryEntry = new DiaryEntry(userId, date);

        assertNotNull(diaryEntry);
        assertEquals(userId, diaryEntry.getUserId());
        assertEquals(date, diaryEntry.getDate());
    }
}
