package uk.ac.cf.spring.nhs.Questionnaire.Model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QuestionnaireUnitTests {

    private Questionnaire questionnaire;

    @BeforeEach
    void setUp() {
        questionnaire = new Questionnaire();
    }

    @Test
    void testTitleCannotBeNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            questionnaire.setTitle(null);
        });
        assertEquals("Title cannot be null", exception.getMessage());
    }

    @Test
    void testTitleMaxLength() {
        String longTitle = "a".repeat(256);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            questionnaire.setTitle(longTitle);
        });
        assertEquals("Title length must be less than or equal to 255 characters", exception.getMessage());
    }

    @Test
    void testDescriptionCanBeNull() {
        assertDoesNotThrow(() -> questionnaire.setDescription(null));
    }

    @Test
    void testCreatedAtIsSetOnPrePersist() {
        questionnaire.onCreate();
        assertNotNull(questionnaire.getCreatedAt(), "CreatedAt should be set");
        assertTrue(questionnaire.getCreatedAt().isBefore(LocalDateTime.now().plusSeconds(1)),
                "CreatedAt should be in the past");
    }

    @Test
    void testIsActiveIsTrueByDefault() {
        questionnaire.onCreate();
        assertTrue(questionnaire.getIsActive(), "isActive should be true by default");
    }

    @Test
    void testSetIsActiveToFalse() {
        questionnaire.setIsActive(false);
        assertFalse(questionnaire.getIsActive(), "isActive should be false");
    }

    @Test
    void testSetCreatedAtManually() {
        LocalDateTime customTime = LocalDateTime.of(2022, 1, 1, 0, 0);
        questionnaire.setCreatedAt(customTime);
        assertEquals(customTime, questionnaire.getCreatedAt(), "CreatedAt should be set to the custom time");
    }

    @Test
    void testQuestionnaireInitializationWithValidData() {
        questionnaire.setTitle("Lymphoedema Awareness");
        questionnaire.setDescription("An educational questionnaire on lymphoedema.");
        questionnaire.onCreate();

        assertEquals("Lymphoedema Awareness", questionnaire.getTitle());
        assertEquals("An educational questionnaire on lymphoedema.", questionnaire.getDescription());
        assertNotNull(questionnaire.getCreatedAt());
        assertTrue(questionnaire.getIsActive());
    }

    @Test
    void testEdgeCaseWithEmptyTitle() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            questionnaire.setTitle("");
        });
        assertEquals("Title cannot be empty", exception.getMessage());
    }

    @Test
    void testEdgeCaseWithWhitespaceTitle() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            questionnaire.setTitle("   ");
        });
        assertEquals("Title cannot be empty", exception.getMessage());
    }
}
