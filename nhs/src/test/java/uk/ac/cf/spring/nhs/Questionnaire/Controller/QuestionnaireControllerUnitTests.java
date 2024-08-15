package uk.ac.cf.spring.nhs.Questionnaire.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
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

import uk.ac.cf.spring.nhs.Questionnaire.Model.Questionnaire;
import uk.ac.cf.spring.nhs.Questionnaire.Service.QuestionnaireService;

class QuestionnaireControllerUnitTests {

    @Mock
    private QuestionnaireService questionnaireService;

    @InjectMocks
    private QuestionnaireController questionnaireController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the retrieval of all questionnaires when the list is empty.
     * 
     * @return void
     */
    @SuppressWarnings("null")
    @Test
    void testGetAllQuestionnairesReturnsEmptyList() {
        when(questionnaireService.getAllQuestionnaires()).thenReturn(new ArrayList<>());

        ResponseEntity<List<Questionnaire>> response = questionnaireController.getAllQuestionnaires();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
        verify(questionnaireService, times(1)).getAllQuestionnaires();
    }

    /**
     * Tests the retrieval of all questionnaires when the list is populated.
     *
     * @return void
     */
    @SuppressWarnings("null")
    @Test
    void testGetAllQuestionnairesReturnsPopulatedList() {
        List<Questionnaire> mockList = new ArrayList<>();
        mockList.add(new Questionnaire());
        mockList.add(new Questionnaire());

        when(questionnaireService.getAllQuestionnaires()).thenReturn(mockList);

        ResponseEntity<List<Questionnaire>> response = questionnaireController.getAllQuestionnaires();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(questionnaireService, times(1)).getAllQuestionnaires();
    }

    /**
     * Tests the retrieval of a questionnaire by ID when the questionnaire is found.
     *
     * @return void
     */
    @SuppressWarnings("null")
    @Test
    void testGetQuestionnaireByIdFound() {
        Questionnaire mockQuestionnaire = new Questionnaire();
        mockQuestionnaire.setId(1L);

        when(questionnaireService.getQuestionnaireById(1L)).thenReturn(Optional.of(mockQuestionnaire));

        ResponseEntity<Questionnaire> response = questionnaireController.getQuestionnaireById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        verify(questionnaireService, times(1)).getQuestionnaireById(1L);
    }

    /**
     * Tests the retrieval of a questionnaire by ID when the questionnaire is not
     * found.
     *
     * @return void
     */
    @Test
    void testGetQuestionnaireByIdNotFound() {
        when(questionnaireService.getQuestionnaireById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Questionnaire> response = questionnaireController.getQuestionnaireById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(questionnaireService, times(1)).getQuestionnaireById(1L);
    }

    /**
     * Tests the retrieval of questionnaires by title.
     *
     * @return void
     */
    @SuppressWarnings("null")
    @Test
    void testSearchQuestionnairesByTitle() {
        Questionnaire questionnaire1 = new Questionnaire();
        questionnaire1.setTitle("Lymphoedema Awareness");

        when(questionnaireService.searchQuestionnairesByTitle("Awareness")).thenReturn(List.of(questionnaire1));

        ResponseEntity<List<Questionnaire>> response = questionnaireController.searchQuestionnairesByTitle("Awareness");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertTrue(response.getBody().get(0).getTitle().contains("Awareness"));
        verify(questionnaireService, times(1)).searchQuestionnairesByTitle("Awareness");
    }

    /**
     * Tests the creation of a new questionnaire.
     *
     * @return void
     */
    @SuppressWarnings("null")
    @Test
    void testCreateQuestionnaire() {
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setTitle("Lymphoedema Awareness");

        when(questionnaireService.createOrUpdateQuestionnaire(any(Questionnaire.class))).thenReturn(questionnaire);

        ResponseEntity<Questionnaire> response = questionnaireController.createQuestionnaire(questionnaire);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Lymphoedema Awareness", response.getBody().getTitle());
        verify(questionnaireService, times(1)).createOrUpdateQuestionnaire(any(Questionnaire.class));
    }

    /**
     * Tests the successful update of a questionnaire.
     *
     * @return void
     */
    @SuppressWarnings("null")
    @Test
    void testUpdateQuestionnaireSuccess() {
        Questionnaire existingQuestionnaire = new Questionnaire();
        existingQuestionnaire.setId(1L);
        existingQuestionnaire.setTitle("Old Title");

        Questionnaire updatedQuestionnaire = new Questionnaire();
        updatedQuestionnaire.setId(1L);
        updatedQuestionnaire.setTitle("Updated Title");

        when(questionnaireService.getQuestionnaireById(1L)).thenReturn(Optional.of(existingQuestionnaire));
        when(questionnaireService.createOrUpdateQuestionnaire(any(Questionnaire.class)))
                .thenReturn(updatedQuestionnaire);

        ResponseEntity<Questionnaire> response = questionnaireController.updateQuestionnaire(1L, updatedQuestionnaire);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated Title", response.getBody().getTitle());
        verify(questionnaireService, times(1)).getQuestionnaireById(1L);
        verify(questionnaireService, times(1)).createOrUpdateQuestionnaire(any(Questionnaire.class));
    }

    /**
     * Tests the update of a questionnaire by ID when the questionnaire is not
     * found.
     *
     * @return void
     */
    @Test
    void testUpdateQuestionnaireNotFound() {
        Questionnaire updatedQuestionnaire = new Questionnaire();
        updatedQuestionnaire.setId(1L);
        updatedQuestionnaire.setTitle("Updated Title");

        when(questionnaireService.getQuestionnaireById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Questionnaire> response = questionnaireController.updateQuestionnaire(1L, updatedQuestionnaire);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(questionnaireService, times(1)).getQuestionnaireById(1L);
        verify(questionnaireService, never()).createOrUpdateQuestionnaire(any(Questionnaire.class));
    }

    /**
     * Tests the successful deletion of a questionnaire.
     *
     * @return void
     */
    @Test
    void testDeleteQuestionnaireSuccess() {
        Questionnaire existingQuestionnaire = new Questionnaire();
        existingQuestionnaire.setId(1L);

        when(questionnaireService.getQuestionnaireById(1L)).thenReturn(Optional.of(existingQuestionnaire));
        doNothing().when(questionnaireService).deleteQuestionnaire(1L);

        ResponseEntity<Void> response = questionnaireController.deleteQuestionnaire(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(questionnaireService, times(1)).getQuestionnaireById(1L);
        verify(questionnaireService, times(1)).deleteQuestionnaire(1L);
    }

    /**
     * Tests the deletion of a questionnaire by ID when the questionnaire is not
     * found.
     *
     * @return void
     */
    @Test
    void testDeleteQuestionnaireNotFound() {
        when(questionnaireService.getQuestionnaireById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = questionnaireController.deleteQuestionnaire(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(questionnaireService, times(1)).getQuestionnaireById(1L);
        verify(questionnaireService, never()).deleteQuestionnaire(1L);
    }
}
