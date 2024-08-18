package uk.ac.cf.spring.nhs.Question.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import uk.ac.cf.spring.nhs.Question.Model.Question;
import uk.ac.cf.spring.nhs.Question.Service.QuestionService;

class QuestionControllerUnitTests {

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private QuestionController questionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the retrieval of all questions when the list is populated.
     *
     * @return void
     */
    @SuppressWarnings("null")
    @Test
    void testGetAllQuestions() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question());
        when(questionService.getQuestionsByQuestionnaireId(null)).thenReturn(questions);

        ResponseEntity<List<Question>> response = questionController.getAllQuestions();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(questionService, times(1)).getQuestionsByQuestionnaireId(null);
    }

    /**
     * Tests the retrieval of questions by questionnaire ID when the list is
     * populated.
     *
     * @return void
     */
    @SuppressWarnings("null")
    @Test
    void testGetQuestionsByQuestionnaireId() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question());
        Long questionnaireId = 1L;
        when(questionService.getQuestionsByQuestionnaireId(questionnaireId)).thenReturn(questions);

        ResponseEntity<List<Question>> response = questionController.getQuestionsByQuestionnaireId(questionnaireId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(questionService, times(1)).getQuestionsByQuestionnaireId(questionnaireId);
    }

    /**
     * Tests the retrieval of a question by ID when the question is not found.
     *
     * @return void
     */
    @Test
    void testGetQuestionByIdNotFound() {
        Long questionId = 1L;
        when(questionService.getQuestionsByQuestionnaireId(questionId)).thenReturn(new ArrayList<>());

        ResponseEntity<Question> response = questionController.getQuestionById(questionId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(questionService, times(1)).getQuestionsByQuestionnaireId(questionId);
    }

    /**
     * Tests the creation of a new question.
     *
     * @return void
     */
    @Test
    void testCreateQuestion() {
        Question question = new Question();
        when(questionService.saveQuestion(question)).thenReturn(question);

        ResponseEntity<Question> response = questionController.createQuestion(question);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(questionService, times(1)).saveQuestion(question);
    }

    /**
     * Tests the updateQuestion method when the question is not found.
     *
     * @return void
     */
    @Test
    void testUpdateQuestionNotFound() {
        Long questionId = 1L;
        Question question = new Question();
        when(questionService.getQuestionsByQuestionnaireId(questionId)).thenReturn(new ArrayList<>());

        ResponseEntity<Question> response = questionController.updateQuestion(questionId, question);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(questionService, times(1)).getQuestionsByQuestionnaireId(questionId);
    }

    /**
     * Tests the deletion of a question by ID when the question is not found.
     *
     * @return void
     */
    @Test
    void testDeleteQuestionNotFound() {
        Long questionId = 1L;
        when(questionService.getQuestionsByQuestionnaireId(questionId)).thenReturn(new ArrayList<>());

        ResponseEntity<Void> response = questionController.deleteQuestion(questionId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(questionService, times(1)).getQuestionsByQuestionnaireId(questionId);
    }
}
