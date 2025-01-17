package uk.ac.cf.spring.nhs.UserQuestionnaire.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.cf.spring.nhs.Questionnaire.Model.Questionnaire;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserQuestionnaireUnitTests {

    private UserQuestionnaire userQuestionnaire;
    private Questionnaire questionnaire;

    @BeforeEach
    void setUp() {
        userQuestionnaire = new UserQuestionnaire();
        questionnaire = new Questionnaire();
        questionnaire.setId(1L);
        questionnaire.setTitle("Test Questionnaire");
    }

    @Test
    void testSetAndGetUserQuestionnaireId() {
        userQuestionnaire.setUserQuestionnaireId(100L);
        assertEquals(100L, userQuestionnaire.getUserQuestionnaireId());
    }

    @Test
    void testSetAndGetQuestionnaire() {
        userQuestionnaire.setQuestionnaire(questionnaire);
        assertEquals(questionnaire, userQuestionnaire.getQuestionnaire());
    }

    @Test
    void testSetAndGetUserID() {
        Long userID = 123L;
        userQuestionnaire.setUserID(userID);
        assertEquals(userID, userQuestionnaire.getUserID());
    }

    @Test
    void testSetAndGetQuestionnaireStartDate() {
        LocalDateTime now = LocalDateTime.now();
        userQuestionnaire.setQuestionnaireStartDate(now);
        assertEquals(now, userQuestionnaire.getQuestionnaireStartDate());
    }

    @Test
    void testSetAndGetQuestionnaireIsCompleted() {
        userQuestionnaire.setQuestionnaireIsCompleted(true);
        assertTrue(userQuestionnaire.getQuestionnaireIsCompleted());
    }

    @Test
    void testSetAndGetQuestionnaireInProgress() {
        userQuestionnaire.setQuestionnaireInProgress(true);
        assertTrue(userQuestionnaire.getQuestionnaireInProgress());
    }

    @Test
    void testQuestionnaireIsCompletedDefaultsToFalse() {
        assertFalse(userQuestionnaire.getQuestionnaireIsCompleted(),
                "QuestionnaireIsCompleted should default to false");
    }

    @Test
    void testQuestionnaireInProgressDefaultsToFalse() {
        assertFalse(userQuestionnaire.getQuestionnaireInProgress(),
                "QuestionnaireInProgress should default to false");
    }

    @Test
    void testSetAndGetQuestionnaireCompletionDate() {
        LocalDateTime now = LocalDateTime.now();
        userQuestionnaire.setQuestionnaireCompletionDate(now);
        assertEquals(now, userQuestionnaire.getQuestionnaireCompletionDate());
    }

    @Test
    void testSetAndGetQuestionnaireDueDate() {
        LocalDate dueDate = LocalDate.of(2025, 6, 15);
        userQuestionnaire.setQuestionnaireDueDate(dueDate);
        assertEquals(dueDate, userQuestionnaire.getQuestionnaireDueDate());
    }

    @Test
    void testSetQuestionnaireToNull() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            userQuestionnaire.setQuestionnaire(null);
        });
        assertEquals("Questionnaire cannot be null", exception.getMessage());
    }

    @Test
    void testSetUserIDToNull() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            userQuestionnaire.setUserID(null);
        });
        assertEquals("User ID cannot be null", exception.getMessage());
    }

    @Test
    void testCompletionDateCanBeNull() {
        userQuestionnaire.setQuestionnaireCompletionDate(null);
        assertNull(userQuestionnaire.getQuestionnaireCompletionDate(), "QuestionnaireCompletionDate should be null");
    }

    @Test
    void testQuestionnaireDueDateCanBeNull() {
        userQuestionnaire.setQuestionnaireDueDate(null);
        assertNull(userQuestionnaire.getQuestionnaireDueDate(), "QuestionnaireDueDate should be null");
    }

    @Test
    void testQuestionnaireStartDateCanBeNull() {
        userQuestionnaire.setQuestionnaireStartDate(null);
        assertNull(userQuestionnaire.getQuestionnaireStartDate(), "QuestionnaireStartDate should be null");
    }
}
