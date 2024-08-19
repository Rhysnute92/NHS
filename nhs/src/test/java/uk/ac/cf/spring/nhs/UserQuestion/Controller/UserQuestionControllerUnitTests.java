package uk.ac.cf.spring.nhs.UserQuestion.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import uk.ac.cf.spring.nhs.Question.Model.Question;
import uk.ac.cf.spring.nhs.UserQuestion.Model.UserQuestion;
import uk.ac.cf.spring.nhs.UserQuestion.Service.UserQuestionService;
import uk.ac.cf.spring.nhs.UserQuestionnaire.Model.UserQuestionnaire;

class UserQuestionControllerUnitTests {

    @Mock
    private UserQuestionService userQuestionService;

    @InjectMocks
    private UserQuestionController userQuestionController;

    private UserQuestion userQuestion;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userQuestion = new UserQuestion();
        userQuestion.setUserQuestionID(1L);

        // Set up UserQuestionnaire
        UserQuestionnaire userQuestionnaire = new UserQuestionnaire();
        userQuestionnaire.setUserQuestionnaireId(1L); // Assuming setter method exists
        userQuestion.setUserQuestionnaire(userQuestionnaire);

        // Set up Question
        Question question = new Question();
        question.setQuestionID(1L); // Assuming setter method exists
        userQuestion.setQuestion(question);
    }

    /**
     * Tests the getUserQuestionsByUserQuestionnaireId method of
     * UserQuestionController
     * to ensure it returns a populated list of UserQuestions when found for the
     * given UserQuestionnaire ID.
     *
     * @return void
     */
    @SuppressWarnings("null")
    @Test
    void testGetUserQuestionsByUserQuestionnaireId() {
        Long userQuestionnaireId = 1L;
        List<UserQuestion> userQuestions = new ArrayList<>();
        userQuestions.add(userQuestion);
        when(userQuestionService.getUserQuestionsByUserQuestionnaireId(userQuestionnaireId)).thenReturn(userQuestions);

        ResponseEntity<List<UserQuestion>> response = userQuestionController
                .getUserQuestionsByUserQuestionnaireId(userQuestionnaireId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(userQuestionService, times(1)).getUserQuestionsByUserQuestionnaireId(userQuestionnaireId);
    }

    /**
     * Tests the getUserQuestionsByQuestionId method of UserQuestionController
     * to ensure it returns a populated list of UserQuestions when found for the
     * given question ID.
     *
     * @return void
     */
    @SuppressWarnings("null")
    @Test
    void testGetUserQuestionsByQuestionId() {
        Long questionId = 1L;
        List<UserQuestion> userQuestions = new ArrayList<>();
        userQuestions.add(userQuestion);
        when(userQuestionService.getUserQuestionsByQuestionId(questionId)).thenReturn(userQuestions);

        ResponseEntity<List<UserQuestion>> response = userQuestionController.getUserQuestionsByQuestionId(questionId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(userQuestionService, times(1)).getUserQuestionsByQuestionId(questionId);
    }

    /**
     * Tests the getUserQuestion method of UserQuestionController.
     * It verifies that the method returns a ResponseEntity with a UserQuestion body
     * when a valid user questionnaire and question ID are provided.
     *
     * @param userQuestionnaireId the ID of the user questionnaire
     * @param questionId          the ID of the question
     * @return void
     */
    @Test
    void testGetUserQuestion() {
        Long userQuestionnaireId = 1L;
        Long questionId = 1L;
        when(userQuestionService.getUserQuestion(userQuestionnaireId, questionId))
                .thenReturn(Optional.of(userQuestion));

        ResponseEntity<UserQuestion> response = userQuestionController.getUserQuestion(userQuestionnaireId, questionId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(userQuestion, response.getBody());
        verify(userQuestionService, times(1)).getUserQuestion(userQuestionnaireId, questionId);
    }

    /**
     * Tests the getUserQuestion method of UserQuestionController to ensure it
     * returns a ResponseEntity with a NOT_FOUND status
     * when an invalid user questionnaire and question ID are provided.
     *
     * @param userQuestionnaireId the ID of the user questionnaire
     * @param questionId          the ID of the question
     * @return void
     */
    @Test
    void testGetUserQuestionNotFound() {
        Long userQuestionnaireId = 1L;
        Long questionId = 1L;
        when(userQuestionService.getUserQuestion(userQuestionnaireId, questionId)).thenReturn(Optional.empty());

        ResponseEntity<UserQuestion> response = userQuestionController.getUserQuestion(userQuestionnaireId, questionId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userQuestionService, times(1)).getUserQuestion(userQuestionnaireId, questionId);
    }

    /**
     * Tests the createUserQuestion method of UserQuestionController to ensure it
     * returns a ResponseEntity with a UserQuestion body
     * when a valid user question is provided.
     *
     * @return void
     */
    @Test
    void testCreateUserQuestion() {
        when(userQuestionService.saveUserQuestion(userQuestion)).thenReturn(userQuestion);

        ResponseEntity<UserQuestion> response = userQuestionController.createUserQuestion(userQuestion);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(userQuestion, response.getBody());
        verify(userQuestionService, times(1)).saveUserQuestion(userQuestion);
    }

    @Test
    void testUpdateUserQuestion() {
        Long questionId = 1L;
        when(userQuestionService.getUserQuestion(userQuestion.getUserQuestionnaire().getUserQuestionnaireId(),
                userQuestion.getQuestion().getQuestionID()))
                .thenReturn(Optional.of(userQuestion));
        when(userQuestionService.saveUserQuestion(userQuestion)).thenReturn(userQuestion);

        ResponseEntity<UserQuestion> response = userQuestionController.updateUserQuestion(questionId, userQuestion);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(userQuestion, response.getBody());
        verify(userQuestionService, times(1)).saveUserQuestion(userQuestion);
    }

    @Test
    void testUpdateUserQuestionNotFound() {
        Long questionId = 1L;
        Long userQuestionnaireId = 1L;

        // Ensure userQuestion has a valid UserQuestionnaire and Question
        when(userQuestionService.getUserQuestion(userQuestionnaireId, questionId)).thenReturn(Optional.empty());

        ResponseEntity<UserQuestion> response = userQuestionController.updateUserQuestion(questionId, userQuestion);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userQuestionService, times(1)).getUserQuestion(userQuestionnaireId, questionId);
    }

    /**
     * Tests the deleteUserQuestion method of UserQuestionController to ensure it
     * returns a ResponseEntity with a NO_CONTENT status
     * when a valid question ID is provided.
     *
     * @return void
     */
    @Test
    void testDeleteUserQuestion() {
        Long questionId = 1L;
        when(userQuestionService.getUserQuestion(null, questionId)).thenReturn(Optional.of(userQuestion));
        doNothing().when(userQuestionService).deleteUserQuestion(questionId);

        ResponseEntity<Void> response = userQuestionController.deleteUserQuestion(questionId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userQuestionService, times(1)).deleteUserQuestion(questionId);
    }

    /**
     * Tests the deleteUserQuestion method of UserQuestionController to ensure it
     * returns a ResponseEntity with a NOT_FOUND status
     * when an invalid question ID is provided.
     *
     * @return void
     */
    @Test
    void testDeleteUserQuestionNotFound() {
        Long questionId = 1L;
        when(userQuestionService.getUserQuestion(null, questionId)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = userQuestionController.deleteUserQuestion(questionId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userQuestionService, times(1)).getUserQuestion(null, questionId);
    }
}
