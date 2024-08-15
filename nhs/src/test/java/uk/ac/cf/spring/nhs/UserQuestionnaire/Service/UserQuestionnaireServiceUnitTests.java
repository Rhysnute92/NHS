package uk.ac.cf.spring.nhs.UserQuestionnaire.Service;

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

import uk.ac.cf.spring.nhs.UserQuestionnaire.Model.UserQuestionnaire;
import uk.ac.cf.spring.nhs.UserQuestionnaire.Repository.JpaUserQuestionnaireRepository;

class UserQuestionnaireServiceUnitTests {

    @Mock
    private JpaUserQuestionnaireRepository userQuestionnaireRepository;

    @InjectMocks
    private UserQuestionnaireService userQuestionnaireService;

    private UserQuestionnaire userQuestionnaire;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userQuestionnaire = new UserQuestionnaire();
        userQuestionnaire.setUserID("user123");
        userQuestionnaire.setQuestionnaireIsCompleted(false);
    }

    /**
     * Tests the getUserQuestionnaires method to ensure it returns an empty list
     * when no user questionnaires are found.
     *
     * @return void
     */
    @Test
    void testGetUserQuestionnairesReturnsEmptyList() {
        String userID = "user123";
        when(userQuestionnaireRepository.findByUserID(userID)).thenReturn(new ArrayList<>());

        List<UserQuestionnaire> result = userQuestionnaireService.getUserQuestionnaires(userID);

        assertNotNull(result);
        assertTrue(result.isEmpty(), "The result list should be empty");
        verify(userQuestionnaireRepository, times(1)).findByUserID(userID);
    }

    /**
     * Tests the getUserQuestionnaires method to ensure it returns a populated list
     * of UserQuestionnaires.
     *
     * @param userID the ID of the user to retrieve questionnaires for
     * @return a list of UserQuestionnaires associated with the specified user
     */
    @Test
    void testGetUserQuestionnairesReturnsPopulatedList() {
        String userID = "user123";
        List<UserQuestionnaire> mockList = new ArrayList<>();
        mockList.add(userQuestionnaire);

        when(userQuestionnaireRepository.findByUserID(userID)).thenReturn(mockList);

        List<UserQuestionnaire> result = userQuestionnaireService.getUserQuestionnaires(userID);

        assertNotNull(result);
        assertEquals(1, result.size(), "The result list should contain 1 item");
        verify(userQuestionnaireRepository, times(1)).findByUserID(userID);
    }

    /**
     * Tests the getCompletedUserQuestionnaires method to ensure it returns an empty
     * list when no completed user questionnaires are found.
     *
     * @return void
     */
    @Test
    void testGetCompletedUserQuestionnairesReturnsEmptyList() {
        String userID = "user123";
        when(userQuestionnaireRepository.findByUserIDAndQuestionnaireIsCompletedTrue(userID))
                .thenReturn(new ArrayList<>());

        List<UserQuestionnaire> result = userQuestionnaireService.getCompletedUserQuestionnaires(userID);

        assertNotNull(result);
        assertTrue(result.isEmpty(), "The result list should be empty");
        verify(userQuestionnaireRepository, times(1)).findByUserIDAndQuestionnaireIsCompletedTrue(userID);
    }

    /**
     * Tests the getCompletedUserQuestionnaires method to ensure it returns a
     * populated list of UserQuestionnaires.
     *
     * @return void
     */
    @Test
    void testGetCompletedUserQuestionnairesReturnsPopulatedList() {
        String userID = "user123";
        userQuestionnaire.setQuestionnaireIsCompleted(true);
        List<UserQuestionnaire> mockList = new ArrayList<>();
        mockList.add(userQuestionnaire);

        when(userQuestionnaireRepository.findByUserIDAndQuestionnaireIsCompletedTrue(userID)).thenReturn(mockList);

        List<UserQuestionnaire> result = userQuestionnaireService.getCompletedUserQuestionnaires(userID);

        assertNotNull(result);
        assertEquals(1, result.size(), "The result list should contain 1 item");
        verify(userQuestionnaireRepository, times(1)).findByUserIDAndQuestionnaireIsCompletedTrue(userID);
    }

    /**
     * Tests the getIncompleteUserQuestionnaires method to ensure it returns an
     * empty list when no incomplete user questionnaires are found.
     *
     * @return void
     */
    @Test
    void testGetIncompleteUserQuestionnairesReturnsEmptyList() {
        String userID = "user123";
        when(userQuestionnaireRepository.findByUserIDAndQuestionnaireIsCompletedFalse(userID))
                .thenReturn(new ArrayList<>());

        List<UserQuestionnaire> result = userQuestionnaireService.getIncompleteUserQuestionnaires(userID);

        assertNotNull(result);
        assertTrue(result.isEmpty(), "The result list should be empty");
        verify(userQuestionnaireRepository, times(1)).findByUserIDAndQuestionnaireIsCompletedFalse(userID);
    }

    /**
     * Tests the getIncompleteUserQuestionnaires method to ensure it returns a
     * populated list of UserQuestionnaires.
     *
     * @param userID the ID of the user to retrieve incomplete questionnaires for
     * @return a list of UserQuestionnaires associated with the specified user
     */
    @Test
    void testGetIncompleteUserQuestionnairesReturnsPopulatedList() {
        String userID = "user123";
        List<UserQuestionnaire> mockList = new ArrayList<>();
        mockList.add(userQuestionnaire);

        when(userQuestionnaireRepository.findByUserIDAndQuestionnaireIsCompletedFalse(userID)).thenReturn(mockList);

        List<UserQuestionnaire> result = userQuestionnaireService.getIncompleteUserQuestionnaires(userID);

        assertNotNull(result);
        assertEquals(1, result.size(), "The result list should contain 1 item");
        verify(userQuestionnaireRepository, times(1)).findByUserIDAndQuestionnaireIsCompletedFalse(userID);
    }

    /**
     * Tests the getUserQuestionnaire method to ensure it returns an empty Optional
     * when no UserQuestionnaire is found.
     *
     * @return void
     */
    @Test
    void testGetUserQuestionnaireReturnsEmptyOptional() {
        String userID = "user123";
        Long questionnaireId = 1L;

        when(userQuestionnaireRepository.findByUserIDAndQuestionnaire_Id(userID, questionnaireId))
                .thenReturn(Optional.empty());

        Optional<UserQuestionnaire> result = userQuestionnaireService.getUserQuestionnaire(userID, questionnaireId);

        assertFalse(result.isPresent(), "The result should be an empty Optional");
        verify(userQuestionnaireRepository, times(1)).findByUserIDAndQuestionnaire_Id(userID,
                questionnaireId);
    }

    /**
     * Tests the getUserQuestionnaire method to ensure it returns a populated
     * Optional when a matching user questionnaire is found.
     *
     * @param userID          the ID of the user to retrieve the questionnaire for
     * @param questionnaireId the ID of the questionnaire to retrieve
     * @return void
     */
    @Test
    void testGetUserQuestionnaireReturnsPopulatedOptional() {
        String userID = "user123";
        Long questionnaireId = 1L;

        when(userQuestionnaireRepository.findByUserIDAndQuestionnaire_Id(userID, questionnaireId))
                .thenReturn(Optional.of(userQuestionnaire));

        Optional<UserQuestionnaire> result = userQuestionnaireService.getUserQuestionnaire(userID, questionnaireId);

        assertTrue(result.isPresent(), "The result should contain a UserQuestionnaire");
        assertEquals(userQuestionnaire, result.get());
        verify(userQuestionnaireRepository, times(1)).findByUserIDAndQuestionnaire_Id(userID,
                questionnaireId);
    }

    /**
     * Tests the saveUserQuestionnaire method to ensure it correctly saves a
     * UserQuestionnaire.
     *
     * @return void
     */
    @Test
    void testSaveUserQuestionnaire() {
        when(userQuestionnaireRepository.save(userQuestionnaire)).thenReturn(userQuestionnaire);

        UserQuestionnaire saved = userQuestionnaireService.saveUserQuestionnaire(userQuestionnaire);

        assertNotNull(saved);
        assertEquals(userQuestionnaire, saved);
        verify(userQuestionnaireRepository, times(1)).save(userQuestionnaire);
    }

    /**
     * Tests the deleteUserQuestionnaire method to ensure it correctly deletes a
     * UserQuestionnaire.
     *
     * @return void
     */
    @Test
    void testDeleteUserQuestionnaire() {
        Long userQuestionnaireId = 1L;
        doNothing().when(userQuestionnaireRepository).deleteById(userQuestionnaireId);

        userQuestionnaireService.deleteUserQuestionnaire(userQuestionnaireId);

        verify(userQuestionnaireRepository, times(1)).deleteById(userQuestionnaireId);
    }

    /**
     * Tests the deleteUserQuestionnaire method to ensure it throws an exception
     * when a UserQuestionnaire is not found.
     *
     * @return void
     */
    @Test
    void testDeleteUserQuestionnaireNotFound() {
        Long userQuestionnaireId = 1L;
        doThrow(new IllegalArgumentException("UserQuestionnaire not found")).when(userQuestionnaireRepository)
                .deleteById(userQuestionnaireId);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userQuestionnaireService.deleteUserQuestionnaire(userQuestionnaireId);
        });

        assertEquals("UserQuestionnaire not found", exception.getMessage());
        verify(userQuestionnaireRepository, times(1)).deleteById(userQuestionnaireId);
    }
}
