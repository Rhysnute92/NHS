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

    /**
     * Test case for the setTitle method when the title is null.
     *
     * This test verifies that when the setTitle method is called with a null title,
     * an IllegalArgumentException is thrown with the message "Title cannot be
     * null".
     *
     * @return void
     */
    @Test
    void testTitleCannotBeNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            questionnaire.setTitle(null);
        });
        assertEquals("Title cannot be null", exception.getMessage());
    }

    /**
     * Test case for the setTitle method when the title exceeds the maximum allowed
     * length.
     *
     * This test verifies that when the setTitle method is called with a title
     * longer than 255 characters,
     * an IllegalArgumentException is thrown with the message "Title length must be
     * less than or equal to 255 characters".
     *
     * @return void
     */
    @Test
    void testTitleMaxLength() {
        String longTitle = "a".repeat(256);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            questionnaire.setTitle(longTitle);
        });
        assertEquals("Title length must be less than or equal to 255 characters", exception.getMessage());
    }

    /**
     * Test case for the setDescription method when the description is null.
     *
     * This test verifies that when the setDescription method is called with a null
     * description,
     * no exception is thrown.
     *
     * @return void
     */
    @Test
    void testDescriptionCanBeNull() {
        assertDoesNotThrow(() -> questionnaire.setDescription(null));
    }

    /**
     * Test case for the onCreate method when it is called.
     *
     * This test verifies that when the onCreate method is called,
     * the createdAt field is set to a date in the past.
     *
     * @return void
     */
    @Test
    void testCreatedAtIsSetOnPrePersist() {
        questionnaire.onCreate();
        assertNotNull(questionnaire.getCreatedAt(), "CreatedAt should be set");
        assertTrue(questionnaire.getCreatedAt().isBefore(LocalDateTime.now().plusSeconds(1)),
                "CreatedAt should be in the past");
    }

    /**
     * Test case for the isActive status when a questionnaire is created.
     *
     * This test verifies that when a questionnaire is created,
     * the isActive status is set to true by default.
     *
     * @return void
     */
    @Test
    void testIsActiveIsTrueByDefault() {
        questionnaire.onCreate();
        assertTrue(questionnaire.getIsActive(), "isActive should be true by default");
    }

    /**
     * Test case for the setIsActive method when setting isActive to false.
     *
     * This test verifies that when the setIsActive method is called with false,
     * the isActive status is correctly updated.
     *
     * @return void
     */
    @Test
    void testSetIsActiveToFalse() {
        questionnaire.setIsActive(false);
        assertFalse(questionnaire.getIsActive(), "isActive should be false");
    }

    /**
     * Test case for setting the createdAt field manually.
     *
     * This test verifies that when the setCreatedAt method is called with a custom
     * time,
     * the createdAt field is correctly updated to the custom time.
     *
     * @return void
     */
    @Test
    void testSetCreatedAtManually() {
        LocalDateTime customTime = LocalDateTime.of(2022, 1, 1, 0, 0);
        questionnaire.setCreatedAt(customTime);
        assertEquals(customTime, questionnaire.getCreatedAt(), "CreatedAt should be set to the custom time");
    }

    /**
     * Test case for the initialization of a questionnaire with valid data.
     *
     * This test verifies that when a questionnaire is initialized with valid data,
     * the title, description, createdAt, and isActive fields are correctly set.
     *
     * @return void
     */
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

    /**
     * Test case for the setTitle method when the title is empty.
     *
     * This test verifies that when the setTitle method is called with an empty
     * title,
     * an IllegalArgumentException is thrown with the message "Title cannot be
     * empty".
     *
     * @return void
     */
    @Test
    void testEdgeCaseWithEmptyTitle() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            questionnaire.setTitle("");
        });
        assertEquals("Title cannot be empty", exception.getMessage());
    }

    /**
     * Test case for the setTitle method when the title contains only whitespace.
     *
     * This test verifies that when the setTitle method is called with a title
     * containing only whitespace,
     * an IllegalArgumentException is thrown with the message "Title cannot be
     * empty".
     *
     * @return void
     */
    @Test
    void testEdgeCaseWithWhitespaceTitle() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            questionnaire.setTitle("   ");
        });
        assertEquals("Title cannot be empty", exception.getMessage());
    }
}
