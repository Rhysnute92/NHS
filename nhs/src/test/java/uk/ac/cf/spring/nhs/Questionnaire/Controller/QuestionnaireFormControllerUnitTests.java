package uk.ac.cf.spring.nhs.Questionnaire.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import uk.ac.cf.spring.nhs.Questionnaire.Model.Questionnaire;
import uk.ac.cf.spring.nhs.Questionnaire.Service.QuestionnaireService;
import uk.ac.cf.spring.nhs.Security.AuthenticationInterface;
import uk.ac.cf.spring.nhs.Security.CustomUserDetails;
import uk.ac.cf.spring.nhs.UserQuestionnaire.Model.UserQuestionnaire;
import uk.ac.cf.spring.nhs.UserQuestionnaire.Service.UserQuestionnaireService;

class QuestionnaireFormControllerUnitTests {

    @Mock
    private QuestionnaireService questionnaireService;

    @Mock
    private AuthenticationInterface authenticationFacade;

    @Mock
    private UserQuestionnaireService userQuestionnaireService;

    @Mock
    private Model model;

    @InjectMocks
    private QuestionnaireFormController questionnaireFormController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Mocking the authentication and user details for all tests
        Authentication authentication = mock(Authentication.class);
        CustomUserDetails userDetails = mock(CustomUserDetails.class);

        when(authenticationFacade.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUserId()).thenReturn(1L);
    }

    @Test
    void testGetQuestionnairePage_WhenQuestionnaireExistsAndUserQuestionnaireExists() {
        // Arrange
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setId(1L);

        UserQuestionnaire userQuestionnaire = new UserQuestionnaire();
        userQuestionnaire.setUserQuestionnaireId(1L);

        when(questionnaireService.getQuestionnaireById(1L)).thenReturn(Optional.of(questionnaire));
        when(userQuestionnaireService.getUserQuestionnaire(anyLong(), anyLong()))
                .thenReturn(Optional.of(userQuestionnaire));

        // Act
        String viewName = questionnaireFormController.getQuestionnairePage(1L, model);

        // Assert
        assertEquals("questionnaire", viewName);
        verify(model, times(1)).addAttribute("questionnaire", questionnaire);
        verify(userQuestionnaireService, times(1)).saveUserQuestionnaire(userQuestionnaire);
    }

    @Test
    void testGetQuestionnairePage_WhenQuestionnaireExistsButUserQuestionnaireDoesNotExist() {
        // Arrange
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setId(1L);

        when(questionnaireService.getQuestionnaireById(1L)).thenReturn(Optional.of(questionnaire));
        when(userQuestionnaireService.getUserQuestionnaire(anyLong(), anyLong()))
                .thenReturn(Optional.empty());

        // Act
        String viewName = questionnaireFormController.getQuestionnairePage(1L, model);

        // Assert
        assertEquals("error/404", viewName);
        verify(model, times(1)).addAttribute("questionnaire", questionnaire);
        verify(userQuestionnaireService, never()).saveUserQuestionnaire(any(UserQuestionnaire.class));
    }

    @Test
    void testGetQuestionnairePage_WhenQuestionnaireDoesNotExist() {
        // Arrange
        when(questionnaireService.getQuestionnaireById(1L)).thenReturn(Optional.empty());

        // Act
        String viewName = questionnaireFormController.getQuestionnairePage(1L, model);

        // Assert
        assertEquals("error/404", viewName);
        verify(model, never()).addAttribute(anyString(), any());
        verify(userQuestionnaireService, never()).saveUserQuestionnaire(any(UserQuestionnaire.class));
    }
}
