package uk.ac.cf.spring.nhs.UserQuestionnaire.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uk.ac.cf.spring.nhs.Questionnaire.Model.Questionnaire;
import uk.ac.cf.spring.nhs.Security.UserCredentials.UserCredentials;

class UserQuestionnaireUnitTests {

    private UserQuestionnaire userQuestionnaire;
    private Questionnaire questionnaire;
    private UserCredentials user;

    @BeforeEach
    void setUp() {
        userQuestionnaire = new UserQuestionnaire();
        questionnaire = new Questionnaire();
        questionnaire.setId(1L);
        questionnaire.setTitle("Test Questionnaire");

        user = new UserCredentials();
/*         user.setId(1L);
        user.setUsername("testuser"); */
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
    void testSetAndGetUser() {
        userQuestionnaire.setUser(user);
        assertEquals(user, userQuestionnaire.getUser());
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
    void testSetAndGetQuestionnaireCompletionDate() {
        LocalDateTime now = LocalDateTime.now();
        userQuestionnaire.setQuestionnaireCompletionDate(now);
        assertEquals(now, userQuestionnaire.getQuestionnaireCompletionDate());
    }

    @Test
    void testQuestionnaireIsCompletedDefaultsToFalse() {
        assertFalse(userQuestionnaire.getQuestionnaireIsCompleted(),
                "QuestionnaireIsCompleted should default to false");
    }

    @Test
    void testSetQuestionnaireToNull() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            userQuestionnaire.setQuestionnaire(null);
        });
        assertEquals("Questionnaire cannot be null", exception.getMessage());
    }

    @Test
    void testSetUserToNull() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            userQuestionnaire.setUser(null);
        });
        assertEquals("User cannot be null", exception.getMessage());
    }

    @Test
    void testSetQuestionnaireStartDateToNull() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            userQuestionnaire.setQuestionnaireStartDate(null);
        });
        assertEquals("Questionnaire start date cannot be null", exception.getMessage());
    }

    @Test
    void testCompletionDateCanBeNull() {
        userQuestionnaire.setQuestionnaireCompletionDate(null);
        assertNull(userQuestionnaire.getQuestionnaireCompletionDate(), "QuestionnaireCompletionDate should be null");
    }
}
