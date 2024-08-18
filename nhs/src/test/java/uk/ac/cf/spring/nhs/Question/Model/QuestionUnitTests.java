package uk.ac.cf.spring.nhs.Question.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uk.ac.cf.spring.nhs.Questionnaire.Model.Questionnaire;

class QuestionUnitTests {

    private Question question;
    private Questionnaire questionnaire;

    @BeforeEach
    void setUp() {
        question = new Question();
        questionnaire = new Questionnaire();
        questionnaire.setId(1L);
        questionnaire.setTitle("Test Questionnaire");
    }

    @Test
    void testSetAndGetQuestionID() {
        question.setQuestionID(100L);
        assertEquals(100L, question.getQuestionID());
    }

    @Test
    void testSetAndGetQuestionText() {
        String questionText = "How does your condition affect your daily life?";
        question.setQuestionText(questionText);
        assertEquals(questionText, question.getQuestionText());
    }

    @Test
    void testSetAndGetQuestionCategory() {
        String questionCategory = "Function";
        question.setQuestionCategory(questionCategory);
        assertEquals(questionCategory, question.getQuestionCategory());
    }

    @Test
    void testSetAndGetQuestionResponseType() {
        String questionResponseType = "Multiple Choice";
        question.setQuestionResponseType(questionResponseType);
        assertEquals(questionResponseType, question.getQuestionResponseType());
    }

    @Test
    void testSetAndGetQuestionnaire() {
        question.setQuestionnaire(questionnaire);
        assertEquals(questionnaire, question.getQuestionnaire());
    }

    @Test
    void testQuestionnaireNotNull() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            question.setQuestionnaire(null);
        });
        assertEquals("Questionnaire cannot be null", exception.getMessage());
    }

    @Test
    void testSetQuestionnaireAndVerifyLink() {
        question.setQuestionnaire(questionnaire);
        assertNotNull(question.getQuestionnaire());
        assertEquals("Test Questionnaire", question.getQuestionnaire().getTitle());
    }

    @Test
    void testDefaultValues() {
        assertNull(question.getQuestionID(), "Question ID should be null by default");
        assertNull(question.getQuestionText(), "Question Text should be null by default");
        assertNull(question.getQuestionCategory(), "Question Category should be null by default");
        assertNull(question.getQuestionResponseType(), "Question Response Type should be null by default");
        assertNull(question.getQuestionnaire(), "Questionnaire should be null by default");
    }

    @Test
    void testSetQuestionCategoryWithLongValue() {
        String longCategory = "A".repeat(256);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            question.setQuestionCategory(longCategory);
        });
        assertEquals("QuestionCategory length must be less than or equal to 255 characters", exception.getMessage());
    }

    @Test
    void testSetQuestionResponseTypeWithLongValue() {
        String longResponseType = "A".repeat(256);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            question.setQuestionResponseType(longResponseType);
        });
        assertEquals("QuestionResponseType length must be less than or equal to 255 characters",
                exception.getMessage());
    }
}
