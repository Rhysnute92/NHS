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
import uk.ac.cf.spring.nhs.Questionnaire.Model.Questionnaire;

class UserQuestionnaireServiceUnitTests {

    @Mock
    private JpaUserQuestionnaireRepository userQuestionnaireRepository;

    @InjectMocks
    private UserQuestionnaireService userQuestionnaireService;

    private UserQuestionnaire userQuestionnaire;
    private Questionnaire questionnaire;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        questionnaire = new Questionnaire();
        questionnaire.setId(1L);
        questionnaire.setTitle("Test Questionnaire");

        userQuestionnaire = new UserQuestionnaire();
        userQuestionnaire.setUserID(123L);
        userQuestionnaire.setQuestionnaire(questionnaire);
        userQuestionnaire.setQuestionnaireIsCompleted(false);
        userQuestionnaire.setQuestionnaireInProgress(false);
    }

    @Test
    void testGetUserQuestionnairesReturnsEmptyList() {
        Long userID = 123L;
        when(userQuestionnaireRepository.findByUserID(userID)).thenReturn(new ArrayList<>());

        List<UserQuestionnaire> result = userQuestionnaireService.getUserQuestionnaires(userID);

        assertNotNull(result);
        assertTrue(result.isEmpty(), "The result list should be empty");
        verify(userQuestionnaireRepository, times(1)).findByUserID(userID);
    }

    @Test
    void testGetUserQuestionnairesReturnsPopulatedList() {
        Long userID = 123L;
        List<UserQuestionnaire> mockList = new ArrayList<>();
        mockList.add(userQuestionnaire);

        when(userQuestionnaireRepository.findByUserID(userID)).thenReturn(mockList);

        List<UserQuestionnaire> result = userQuestionnaireService.getUserQuestionnaires(userID);

        assertNotNull(result);
        assertEquals(1, result.size(), "The result list should contain 1 item");
        verify(userQuestionnaireRepository, times(1)).findByUserID(userID);
    }

    @Test
    void testGetCompletedUserQuestionnairesReturnsEmptyList() {
        Long userID = 123L;
        when(userQuestionnaireRepository.findByUserIDAndQuestionnaireIsCompletedTrue(userID))
                .thenReturn(new ArrayList<>());

        List<UserQuestionnaire> result = userQuestionnaireService.getCompletedUserQuestionnaires(userID);

        assertNotNull(result);
        assertTrue(result.isEmpty(), "The result list should be empty");
        verify(userQuestionnaireRepository, times(1)).findByUserIDAndQuestionnaireIsCompletedTrue(userID);
    }

    @Test
    void testGetCompletedUserQuestionnairesReturnsPopulatedList() {
        Long userID = 123L;
        userQuestionnaire.setQuestionnaireIsCompleted(true);
        List<UserQuestionnaire> mockList = new ArrayList<>();
        mockList.add(userQuestionnaire);

        when(userQuestionnaireRepository.findByUserIDAndQuestionnaireIsCompletedTrue(userID)).thenReturn(mockList);

        List<UserQuestionnaire> result = userQuestionnaireService.getCompletedUserQuestionnaires(userID);

        assertNotNull(result);
        assertEquals(1, result.size(), "The result list should contain 1 item");
        verify(userQuestionnaireRepository, times(1)).findByUserIDAndQuestionnaireIsCompletedTrue(userID);
    }

    @Test
    void testGetIncompleteUserQuestionnairesReturnsEmptyList() {
        Long userID = 123L;
        when(userQuestionnaireRepository.findByUserIDAndQuestionnaireIsCompletedFalse(userID))
                .thenReturn(new ArrayList<>());

        List<UserQuestionnaire> result = userQuestionnaireService.getIncompleteUserQuestionnaires(userID);

        assertNotNull(result);
        assertTrue(result.isEmpty(), "The result list should be empty");
        verify(userQuestionnaireRepository, times(1)).findByUserIDAndQuestionnaireIsCompletedFalse(userID);
    }

    @Test
    void testGetIncompleteUserQuestionnairesReturnsPopulatedList() {
        Long userID = 123L;
        List<UserQuestionnaire> mockList = new ArrayList<>();
        mockList.add(userQuestionnaire);

        when(userQuestionnaireRepository.findByUserIDAndQuestionnaireIsCompletedFalse(userID)).thenReturn(mockList);

        List<UserQuestionnaire> result = userQuestionnaireService.getIncompleteUserQuestionnaires(userID);

        assertNotNull(result);
        assertEquals(1, result.size(), "The result list should contain 1 item");
        verify(userQuestionnaireRepository, times(1)).findByUserIDAndQuestionnaireIsCompletedFalse(userID);
    }

    @Test
    void testGetUserQuestionnaireReturnsEmptyOptional() {
        Long userID = 123L;
        Long questionnaireId = 1L;

        when(userQuestionnaireRepository.findByUserIDAndQuestionnaire_Id(userID, questionnaireId))
                .thenReturn(Optional.empty());

        Optional<UserQuestionnaire> result = userQuestionnaireService.getUserQuestionnaire(userID, questionnaireId);

        assertFalse(result.isPresent(), "The result should be an empty Optional");
        verify(userQuestionnaireRepository, times(1)).findByUserIDAndQuestionnaire_Id(userID, questionnaireId);
    }

    @Test
    void testGetUserQuestionnaireReturnsPopulatedOptional() {
        Long userID = 123L;
        Long questionnaireId = 1L;

        when(userQuestionnaireRepository.findByUserIDAndQuestionnaire_Id(userID, questionnaireId))
                .thenReturn(Optional.of(userQuestionnaire));

        Optional<UserQuestionnaire> result = userQuestionnaireService.getUserQuestionnaire(userID, questionnaireId);

        assertTrue(result.isPresent(), "The result should contain a UserQuestionnaire");
        assertEquals(userQuestionnaire, result.get());
        verify(userQuestionnaireRepository, times(1)).findByUserIDAndQuestionnaire_Id(userID, questionnaireId);
    }

    @Test
    void testSaveUserQuestionnaire() {
        when(userQuestionnaireRepository.save(userQuestionnaire)).thenReturn(userQuestionnaire);

        UserQuestionnaire saved = userQuestionnaireService.saveUserQuestionnaire(userQuestionnaire);

        assertNotNull(saved);
        assertEquals(userQuestionnaire, saved);
        verify(userQuestionnaireRepository, times(1)).save(userQuestionnaire);
    }

    @Test
    void testDeleteUserQuestionnaire() {
        Long userQuestionnaireId = 1L;
        doNothing().when(userQuestionnaireRepository).deleteById(userQuestionnaireId);

        userQuestionnaireService.deleteUserQuestionnaire(userQuestionnaireId);

        verify(userQuestionnaireRepository, times(1)).deleteById(userQuestionnaireId);
    }

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
