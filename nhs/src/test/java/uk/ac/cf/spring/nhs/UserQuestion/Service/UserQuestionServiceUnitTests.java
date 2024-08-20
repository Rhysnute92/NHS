package uk.ac.cf.spring.nhs.UserQuestion.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.ac.cf.spring.nhs.UserQuestion.Model.UserQuestion;
import uk.ac.cf.spring.nhs.UserQuestion.Repository.JpaUserQuestionRepository;

class UserQuestionServiceUnitTests {

    @Mock
    private JpaUserQuestionRepository userQuestionRepository;

    @InjectMocks
    private UserQuestionService userQuestionService;

    private UserQuestion userQuestion;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userQuestion = new UserQuestion();
        userQuestion.setUserQuestionID(1L);
    }

    /**
     * Tests the getUserQuestionsByUserQuestionnaireId method of UserQuestionService
     * to ensure it returns an empty list when no UserQuestions are found for the
     * given UserQuestionnaire ID.
     *
     * @return void
     */
    @Test
    void testGetUserQuestionsByUserQuestionnaireIdReturnsEmptyList() {
        Long userQuestionnaireId = 1L;
        when(userQuestionRepository.findByUserQuestionnaire_UserQuestionnaireId(userQuestionnaireId))
                .thenReturn(new ArrayList<>());

        List<UserQuestion> result = userQuestionService.getUserQuestionsByUserQuestionnaireId(userQuestionnaireId);

        assertNotNull(result);
        assertTrue(result.isEmpty(), "The result list should be empty");
        verify(userQuestionRepository, times(1)).findByUserQuestionnaire_UserQuestionnaireId(userQuestionnaireId);
    }

    /**
     * Tests the getUserQuestionsByUserQuestionnaireId method of UserQuestionService
     * to ensure it returns a populated list when UserQuestions are found for the
     * given UserQuestionnaire ID.
     *
     * @return void
     */
    @Test
    void testGetUserQuestionsByUserQuestionnaireIdReturnsPopulatedList() {
        Long userQuestionnaireId = 1L;
        List<UserQuestion> mockList = new ArrayList<>();
        mockList.add(userQuestion);

        when(userQuestionRepository.findByUserQuestionnaire_UserQuestionnaireId(userQuestionnaireId))
                .thenReturn(mockList);

        List<UserQuestion> result = userQuestionService.getUserQuestionsByUserQuestionnaireId(userQuestionnaireId);

        assertNotNull(result);
        assertEquals(1, result.size(), "The result list should contain 1 item");
        verify(userQuestionRepository, times(1)).findByUserQuestionnaire_UserQuestionnaireId(userQuestionnaireId);
    }

    /**
     * Tests the getUserQuestionsByQuestionId method of UserQuestionService
     * to ensure it returns an empty list when no UserQuestions are found for the
     * given Question ID.
     *
     * @return void
     */
    @Test
    void testGetUserQuestionsByQuestionIdReturnsEmptyList() {
        Long questionId = 1L;
        when(userQuestionRepository.findByQuestion_QuestionID(questionId)).thenReturn(new ArrayList<>());

        List<UserQuestion> result = userQuestionService.getUserQuestionsByQuestionId(questionId);

        assertNotNull(result);
        assertTrue(result.isEmpty(), "The result list should be empty");
        verify(userQuestionRepository, times(1)).findByQuestion_QuestionID(questionId);
    }

    /**
     * Tests the getUserQuestionsByQuestionId method of UserQuestionService
     * to ensure it returns a populated list when UserQuestions are found for the
     * given Question ID.
     *
     * @return void
     */
    @Test
    void testGetUserQuestionsByQuestionIdReturnsPopulatedList() {
        Long questionId = 1L;
        List<UserQuestion> mockList = new ArrayList<>();
        mockList.add(userQuestion);

        when(userQuestionRepository.findByQuestion_QuestionID(questionId)).thenReturn(mockList);

        List<UserQuestion> result = userQuestionService.getUserQuestionsByQuestionId(questionId);

        assertNotNull(result);
        assertEquals(1, result.size(), "The result list should contain 1 item");
        verify(userQuestionRepository, times(1)).findByQuestion_QuestionID(questionId);
    }

    /**
     * Tests the getUserQuestion method of UserQuestionService
     * to ensure it returns an empty Optional when no UserQuestion is found for the
     * given UserQuestionnaire ID and Question ID.
     *
     * @return void
     */
    @Test
    void testGetUserQuestionReturnsEmptyOptional() {
        Long userQuestionnaireId = 1L;
        Long questionId = 1L;

        when(userQuestionRepository
                .findByUserQuestionnaire_UserQuestionnaireIdAndQuestion_QuestionID(userQuestionnaireId, questionId))
                .thenReturn(null);

        Optional<UserQuestion> result = userQuestionService.getUserQuestion(userQuestionnaireId, questionId);

        assertFalse(result.isPresent(), "The result should be an empty Optional");
        verify(userQuestionRepository, times(1))
                .findByUserQuestionnaire_UserQuestionnaireIdAndQuestion_QuestionID(userQuestionnaireId, questionId);
    }

    /**
     * Tests the getUserQuestion method of UserQuestionService
     * to ensure it returns a populated Optional when a UserQuestion is found for
     * the given UserQuestionnaire ID and Question ID.
     *
     * @return void
     */
    @Test
    void testGetUserQuestionReturnsPopulatedOptional() {
        Long userQuestionnaireId = 1L;
        Long questionId = 1L;

        when(userQuestionRepository
                .findByUserQuestionnaire_UserQuestionnaireIdAndQuestion_QuestionID(userQuestionnaireId, questionId))
                .thenReturn(userQuestion);

        Optional<UserQuestion> result = userQuestionService.getUserQuestion(userQuestionnaireId, questionId);

        assertTrue(result.isPresent(), "The result should contain a UserQuestion");
        assertEquals(userQuestion, result.get());
        verify(userQuestionRepository, times(1))
                .findByUserQuestionnaire_UserQuestionnaireIdAndQuestion_QuestionID(userQuestionnaireId, questionId);
    }

    /**
     * Tests the saveUserQuestion method of UserQuestionService
     * to ensure it correctly saves a UserQuestion.
     *
     * @return void
     */
    @Test
    void testSaveUserQuestion() {
        when(userQuestionRepository.save(userQuestion)).thenReturn(userQuestion);

        UserQuestion saved = userQuestionService.saveUserQuestion(userQuestion);

        assertNotNull(saved);
        assertEquals(userQuestion, saved);
        verify(userQuestionRepository, times(1)).save(userQuestion);
    }

    /**
     * Tests the deleteUserQuestion method of UserQuestionService
     * to ensure it correctly deletes a UserQuestion.
     *
     * @return void
     */
    @Test
    void testDeleteUserQuestion() {
        Long userQuestionId = 1L;
        doNothing().when(userQuestionRepository).deleteById(userQuestionId);

        userQuestionService.deleteUserQuestion(userQuestionId);

        verify(userQuestionRepository, times(1)).deleteById(userQuestionId);
    }

    /**
     * Tests the deleteUserQuestion method of UserQuestionService
     * to ensure it throws an exception when a UserQuestion is not found.
     *
     * @return void
     */
    @Test
    void testDeleteUserQuestionNotFound() {
        Long userQuestionId = 1L;
        doThrow(new IllegalArgumentException("UserQuestion not found")).when(userQuestionRepository)
                .deleteById(userQuestionId);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userQuestionService.deleteUserQuestion(userQuestionId);
        });

        assertEquals("UserQuestion not found", exception.getMessage());
        verify(userQuestionRepository, times(1)).deleteById(userQuestionId);
    }

}