package uk.ac.cf.spring.nhs.UserQuestionnaire.Controller;

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

import uk.ac.cf.spring.nhs.UserQuestionnaire.Model.UserQuestionnaire;
import uk.ac.cf.spring.nhs.UserQuestionnaire.Service.UserQuestionnaireService;
import uk.ac.cf.spring.nhs.Questionnaire.Model.Questionnaire;

class UserQuestionnaireControllerUnitTests {

    @Mock
    private UserQuestionnaireService userQuestionnaireService;

    @InjectMocks
    private UserQuestionnaireController userQuestionnaireController;

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
    void testGetUserQuestionnaires() {
        Long userID = 123L;
        List<UserQuestionnaire> userQuestionnaires = new ArrayList<>();
        userQuestionnaires.add(userQuestionnaire);
        when(userQuestionnaireService.getUserQuestionnaires(userID)).thenReturn(userQuestionnaires);

        ResponseEntity<List<UserQuestionnaire>> response = userQuestionnaireController.getUserQuestionnaires(userID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(userQuestionnaireService, times(1)).getUserQuestionnaires(userID);
    }

    @Test
    void testGetCompletedUserQuestionnaires() {
        Long userID = 123L;
        List<UserQuestionnaire> userQuestionnaires = new ArrayList<>();
        userQuestionnaire.setQuestionnaireIsCompleted(true);
        userQuestionnaires.add(userQuestionnaire);
        when(userQuestionnaireService.getCompletedUserQuestionnaires(userID)).thenReturn(userQuestionnaires);

        ResponseEntity<List<UserQuestionnaire>> response = userQuestionnaireController
                .getCompletedUserQuestionnaires(userID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(userQuestionnaireService, times(1)).getCompletedUserQuestionnaires(userID);
    }

    @Test
    void testGetIncompleteUserQuestionnaires() {
        Long userID = 123L;
        List<UserQuestionnaire> userQuestionnaires = new ArrayList<>();
        userQuestionnaire.setQuestionnaireIsCompleted(false);
        userQuestionnaires.add(userQuestionnaire);
        when(userQuestionnaireService.getIncompleteUserQuestionnaires(userID)).thenReturn(userQuestionnaires);

        ResponseEntity<List<UserQuestionnaire>> response = userQuestionnaireController
                .getIncompleteUserQuestionnaires(userID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(userQuestionnaireService, times(1)).getIncompleteUserQuestionnaires(userID);
    }

    @Test
    void testGetUserQuestionnaire() {
        Long userID = 123L;
        Long questionnaireId = 1L;
        when(userQuestionnaireService.getUserQuestionnaire(userID, questionnaireId))
                .thenReturn(Optional.of(userQuestionnaire));

        ResponseEntity<UserQuestionnaire> response = userQuestionnaireController.getUserQuestionnaire(userID,
                questionnaireId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(userQuestionnaire, response.getBody());
        verify(userQuestionnaireService, times(1)).getUserQuestionnaire(userID, questionnaireId);
    }

    @Test
    void testGetUserQuestionnaireNotFound() {
        Long userID = 123L;
        Long questionnaireId = 1L;
        when(userQuestionnaireService.getUserQuestionnaire(userID, questionnaireId)).thenReturn(Optional.empty());

        ResponseEntity<UserQuestionnaire> response = userQuestionnaireController.getUserQuestionnaire(userID,
                questionnaireId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userQuestionnaireService, times(1)).getUserQuestionnaire(userID, questionnaireId);
    }

    @Test
    void testCreateUserQuestionnaire() {
        when(userQuestionnaireService.saveUserQuestionnaire(userQuestionnaire)).thenReturn(userQuestionnaire);

        ResponseEntity<UserQuestionnaire> response = userQuestionnaireController
                .createUserQuestionnaire(userQuestionnaire);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(userQuestionnaire, response.getBody());
        verify(userQuestionnaireService, times(1)).saveUserQuestionnaire(userQuestionnaire);
    }

    @Test
    void testUpdateUserQuestionnaire() {
        Long questionnaireId = 1L;
        when(userQuestionnaireService.getUserQuestionnaire(userQuestionnaire.getUserID(), questionnaireId))
                .thenReturn(Optional.of(userQuestionnaire));
        when(userQuestionnaireService.saveUserQuestionnaire(userQuestionnaire)).thenReturn(userQuestionnaire);

        ResponseEntity<UserQuestionnaire> response = userQuestionnaireController
                .updateUserQuestionnaire(questionnaireId, userQuestionnaire);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(userQuestionnaire, response.getBody());
        verify(userQuestionnaireService, times(1)).saveUserQuestionnaire(userQuestionnaire);
    }

    @Test
    void testUpdateUserQuestionnaireNotFound() {
        Long questionnaireId = 1L;
        when(userQuestionnaireService.getUserQuestionnaire(userQuestionnaire.getUserID(), questionnaireId))
                .thenReturn(Optional.empty());

        ResponseEntity<UserQuestionnaire> response = userQuestionnaireController
                .updateUserQuestionnaire(questionnaireId, userQuestionnaire);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userQuestionnaireService, times(1)).getUserQuestionnaire(userQuestionnaire.getUserID(), questionnaireId);
    }

    @Test
    void testDeleteUserQuestionnaire() {
        Long questionnaireId = 1L;
        when(userQuestionnaireService.getUserQuestionnaire(null, questionnaireId))
                .thenReturn(Optional.of(userQuestionnaire));
        doNothing().when(userQuestionnaireService).deleteUserQuestionnaire(questionnaireId);

        ResponseEntity<Void> response = userQuestionnaireController.deleteUserQuestionnaire(questionnaireId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userQuestionnaireService, times(1)).deleteUserQuestionnaire(questionnaireId);
    }

    @Test
    void testDeleteUserQuestionnaireNotFound() {
        Long questionnaireId = 1L;
        when(userQuestionnaireService.getUserQuestionnaire(null, questionnaireId)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = userQuestionnaireController.deleteUserQuestionnaire(questionnaireId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userQuestionnaireService, times(1)).getUserQuestionnaire(null, questionnaireId);
    }
}
