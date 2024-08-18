package uk.ac.cf.spring.nhs.Question.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
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

import uk.ac.cf.spring.nhs.Question.Model.Question;
import uk.ac.cf.spring.nhs.Question.Repository.JpaQuestionRepo;
import uk.ac.cf.spring.nhs.Questionnaire.Model.Questionnaire;

public class QuestionServiceUnitTests {
    @Mock
    private JpaQuestionRepo questionRepository;

    @InjectMocks
    private QuestionService questionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the getQuestionsByQuestionnaireId method of QuestionService
     * to ensure it returns an empty list when no Questions are found for the given
     * Questionnaire ID.
     *
     * @return void
     */
    @Test
    void testGetQuestionsByQuestionnaireIdReturnsEmptyList() {
        Long questionnaireId = 1L;
        when(questionRepository.findByQuestionnaireId(questionnaireId)).thenReturn(new ArrayList<>());

        List<Question> questions = questionService.getQuestionsByQuestionnaireId(questionnaireId);

        assertNotNull(questions);
        assertTrue(questions.isEmpty(), "The list should be empty");
        verify(questionRepository, times(1)).findByQuestionnaireId(questionnaireId);
    }

    /**
     * Tests the getQuestionsByQuestionnaireId method of QuestionService
     * to ensure it returns a populated list when Questions are found for the given
     * Questionnaire ID.
     *
     * @return void
     */
    @Test
    void testGetQuestionsByQuestionnaireIdReturnsPopulatedList() {
        Long questionnaireId = 1L;
        List<Question> mockQuestions = new ArrayList<>();
        mockQuestions.add(new Question());
        mockQuestions.add(new Question());

        when(questionRepository.findByQuestionnaireId(questionnaireId)).thenReturn(mockQuestions);

        List<Question> questions = questionService.getQuestionsByQuestionnaireId(questionnaireId);

        assertNotNull(questions);
        assertEquals(2, questions.size(), "The list should contain 2 questions");
        verify(questionRepository, times(1)).findByQuestionnaireId(questionnaireId);
    }

    /**
     * Tests the getQuestionsByCategory method of QuestionService
     * to ensure it returns an empty list when no Questions are found for the given
     * category.
     *
     * @return void
     */
    @Test
    void testGetQuestionsByCategoryReturnsEmptyList() {
        String category = "Function";
        when(questionRepository.findByQuestionCategory(category)).thenReturn(new ArrayList<>());

        List<Question> questions = questionService.getQuestionsByCategory(category);

        assertNotNull(questions);
        assertTrue(questions.isEmpty(), "The list should be empty");
        verify(questionRepository, times(1)).findByQuestionCategory(category);
    }

    /**
     * Tests the getQuestionsByCategory method of QuestionService
     * to ensure it returns a populated list when Questions are found for the given
     * category.
     *
     * @return void
     */
    @Test
    void testGetQuestionsByCategoryReturnsPopulatedList() {
        String category = "Function";
        List<Question> mockQuestions = new ArrayList<>();
        mockQuestions.add(new Question());
        mockQuestions.add(new Question());

        when(questionRepository.findByQuestionCategory(category)).thenReturn(mockQuestions);

        List<Question> questions = questionService.getQuestionsByCategory(category);

        assertNotNull(questions);
        assertEquals(2, questions.size(), "The list should contain 2 questions");
        verify(questionRepository, times(1)).findByQuestionCategory(category);
    }

    /**
     * Tests the getQuestionsByResponseType method of QuestionService
     * to ensure it returns an empty list when no Questions are found for the given
     * response type.
     *
     * @return void
     */
    @Test
    void testGetQuestionsByResponseTypeReturnsEmptyList() {
        String responseType = "Multiple Choice";
        when(questionRepository.findByQuestionResponseType(responseType)).thenReturn(new ArrayList<>());

        List<Question> questions = questionService.getQuestionsByResponseType(responseType);

        assertNotNull(questions);
        assertTrue(questions.isEmpty(), "The list should be empty");
        verify(questionRepository, times(1)).findByQuestionResponseType(responseType);
    }

    /**
     * Tests the getQuestionsByResponseType method of QuestionService
     * to ensure it returns a populated list when Questions are found for the given
     * response type.
     *
     * @return void
     */
    @Test
    void testGetQuestionsByResponseTypeReturnsPopulatedList() {
        String responseType = "Multiple Choice";
        List<Question> mockQuestions = new ArrayList<>();
        mockQuestions.add(new Question());
        mockQuestions.add(new Question());

        when(questionRepository.findByQuestionResponseType(responseType)).thenReturn(mockQuestions);

        List<Question> questions = questionService.getQuestionsByResponseType(responseType);

        assertNotNull(questions);
        assertEquals(2, questions.size(), "The list should contain 2 questions");
        verify(questionRepository, times(1)).findByQuestionResponseType(responseType);
    }

    /**
     * Tests the saveQuestion method of QuestionService to ensure it correctly saves
     * a Question.
     *
     * @return void
     */
    @Test
    void testSaveQuestion() {
        // Create a mock Questionnaire
        Questionnaire questionnaire = new Questionnaire();
        // Create a Question object and set the Questionnaire
        Question question = new Question();
        question.setQuestionnaire(questionnaire);

        // Mock the repository save method
        when(questionRepository.save(question)).thenReturn(question);

        // Call the service method
        Question savedQuestion = questionService.saveQuestion(question);

        // Verify the result
        assertNotNull(savedQuestion);
        verify(questionRepository, times(1)).save(question);
    }

    /**
     * Tests the saveQuestion method of QuestionService to ensure it throws a
     * NullPointerException when a Question with a null questionnaire is passed.
     *
     * @return void
     */
    @Test
    void testSaveQuestionWithNullQuestionnaire() {
        Question question = new Question();

        Exception exception = assertThrows(NullPointerException.class, () -> {
            questionService.saveQuestion(question);
        });

        assertEquals("Questionnaire cannot be null", exception.getMessage());
        verify(questionRepository, times(0)).save(question);
    }

    /**
     * Tests the deleteQuestion method of QuestionService to ensure it correctly
     * deletes a Question.
     *
     * @return void
     */
    @Test
    void testDeleteQuestion() {
        Long questionId = 1L;
        doNothing().when(questionRepository).deleteById(questionId);

        questionService.deleteQuestion(questionId);

        verify(questionRepository, times(1)).deleteById(questionId);
    }

    /**
     * Tests the deleteQuestion method of QuestionService to ensure it throws an
     * exception when a Question is not found.
     *
     * @return void
     */
    @Test
    void testDeleteQuestionNotFound() {
        Long questionId = 1L;
        doThrow(new IllegalArgumentException("Question not found")).when(questionRepository).deleteById(questionId);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            questionService.deleteQuestion(questionId);
        });

        assertEquals("Question not found", exception.getMessage());
        verify(questionRepository, times(1)).deleteById(questionId);
    }

}
