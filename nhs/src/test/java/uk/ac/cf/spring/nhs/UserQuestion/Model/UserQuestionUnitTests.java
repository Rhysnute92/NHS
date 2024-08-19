package uk.ac.cf.spring.nhs.UserQuestion.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uk.ac.cf.spring.nhs.Question.Model.Question;
import uk.ac.cf.spring.nhs.UserQuestionnaire.Model.UserQuestionnaire;

class UserQuestionUnitTests {

    private UserQuestion userQuestion;
    private UserQuestionnaire userQuestionnaire;
    private Question question;

    @BeforeEach
    void setUp() {
        userQuestion = new UserQuestion();
        userQuestionnaire = new UserQuestionnaire();
        userQuestionnaire.setUserQuestionnaireId(1L);

        question = new Question();
        question.setQuestionID(1L);
    }

    /**
     * Tests the setUserQuestionID and getUserQuestionID methods to ensure they are
     * setting and retrieving the user question ID correctly.
     *
     * @return void
     */
    @Test
    void testSetAndGetUserQuestionID() {
        userQuestion.setUserQuestionID(100L);
        assertEquals(100L, userQuestion.getUserQuestionID());
    }

    /**
     * Tests the setUserQuestionnaire and getUserQuestionnaire methods to ensure
     * they are setting and retrieving the user questionnaire correctly.
     *
     * @return void
     */
    @Test
    void testSetAndGetUserQuestionnaire() {
        userQuestion.setUserQuestionnaire(userQuestionnaire);
        assertEquals(userQuestionnaire, userQuestion.getUserQuestionnaire());
    }

    /**
     * Tests the setQuestion and getQuestion methods to ensure they are setting and
     * retrieving the question correctly.
     *
     * @return void
     */
    @Test
    void testSetAndGetQuestion() {
        userQuestion.setQuestion(question);
        assertEquals(question, userQuestion.getQuestion());
    }

    /**
     * Tests the setUserResponseText and getUserResponseText methods to ensure they
     * are setting and retrieving the user response text correctly.
     *
     * @return void
     */
    @Test
    void testSetAndGetUserResponseText() {
        String responseText = "This is a sample response.";
        userQuestion.setUserResponseText(responseText);
        assertEquals(responseText, userQuestion.getUserResponseText());
    }

    /**
     * Tests the setUserResponseScore and getUserResponseScore methods to ensure
     * they are setting and retrieving the user response score correctly.
     *
     * @return void
     */
    @Test
    void testSetAndGetUserResponseScore() {
        Integer responseScore = 5;
        userQuestion.setUserResponseScore(responseScore);
        assertEquals(responseScore, userQuestion.getUserResponseScore());
    }

    /**
     * Tests whether the response date time is set correctly when the onCreate event
     * is triggered.
     *
     * @return void
     */
    @Test
    void testResponseDateTimeIsSetOnCreate() {
        LocalDateTime before = LocalDateTime.now();

        userQuestion.onCreate();

        LocalDateTime responseDateTime = userQuestion.getResponseDateTime();
        LocalDateTime after = LocalDateTime.now();

        assertNotNull(responseDateTime, "ResponseDateTime should be set");
        assertTrue(!responseDateTime.isBefore(before) && !responseDateTime.isAfter(after),
                "ResponseDateTime should be within the expected range");
    }

    /**
     * Tests the setUserQuestionnaire method to ensure it throws a
     * NullPointerException when a null user questionnaire is passed.
     *
     * @return void
     */
    @Test
    void testSetUserQuestionnaireToNull() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            userQuestion.setUserQuestionnaire(null);
        });
        assertEquals("UserQuestionnaire cannot be null", exception.getMessage());
    }

    /**
     * Tests the setQuestion method to ensure it throws a NullPointerException when
     * a null question is passed.
     *
     * @return void
     */
    @Test
    void testSetQuestionToNull() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            userQuestion.setQuestion(null);
        });
        assertEquals("Question cannot be null", exception.getMessage());
    }

    /**
     * Tests the setResponseDateTime method by setting a custom date and time and
     * verifying that the responseDateTime field is correctly updated.
     *
     * @param customDateTime the custom date and time to set
     * @return void
     */
    @Test
    void testSetResponseDateTimeManually() {
        LocalDateTime customDateTime = LocalDateTime.of(2023, 1, 1, 12, 0);
        userQuestion.setResponseDateTime(customDateTime);
        assertEquals(customDateTime, userQuestion.getResponseDateTime(),
                "ResponseDateTime should be set to the custom time");
    }
}
