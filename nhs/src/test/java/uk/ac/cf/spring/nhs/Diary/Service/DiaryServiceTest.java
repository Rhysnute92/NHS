package uk.ac.cf.spring.nhs.Diary.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.cf.spring.nhs.Diary.Entity.DiaryEntry;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DiaryServiceTest {

    private DiaryService diaryService;

    @BeforeEach
    void setUp() {
        diaryService = new DiaryService();
    }

    @Test
    void getDiaryEntriesReturnsNonEmptyList() {
        List<DiaryEntry> diaryEntries = diaryService.getDiaryEntries();
        assertFalse(diaryEntries.isEmpty());
    }

    @Test
    void getDiaryEntriesReturnsEntriesWithNonNullContent() {
        List<DiaryEntry> diaryEntries = diaryService.getDiaryEntries();
        for (DiaryEntry diaryEntry : diaryEntries) {
            assertNotNull(diaryEntry.getContent());
        }
    }
}