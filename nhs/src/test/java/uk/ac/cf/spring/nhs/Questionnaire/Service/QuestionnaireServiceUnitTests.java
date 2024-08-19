package uk.ac.cf.spring.nhs.Questionnaire.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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

import uk.ac.cf.spring.nhs.Questionnaire.Model.Questionnaire;
import uk.ac.cf.spring.nhs.Questionnaire.Repository.JpaQuestionnaireRepo;

public class QuestionnaireServiceUnitTests {
    @Mock
    private JpaQuestionnaireRepo questionnaireRepository;

    @InjectMocks
    private QuestionnaireService questionnaireService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the retrieval of all questionnaires when the repository is empty.
     * 
     * @return none
     */
    @Test
    void testGetAllQuestionnairesReturnsEmptyList() {
        when(questionnaireRepository.findAll()).thenReturn(new ArrayList<>());

        List<Questionnaire> questionnaires = questionnaireService.getAllQuestionnaires();

        assertNotNull(questionnaires);
        assertTrue(questionnaires.isEmpty(), "The list should be empty");
        verify(questionnaireRepository, times(1)).findAll();
    }

    /**
     * Tests the retrieval of all questionnaires when the repository contains a
     * populated list.
     * 
     * @return none
     */
    @Test
    void testGetAllQuestionnairesReturnsPopulatedList() {
        List<Questionnaire> mockList = new ArrayList<>();
        mockList.add(new Questionnaire());
        mockList.add(new Questionnaire());

        when(questionnaireRepository.findAll()).thenReturn(mockList);

        List<Questionnaire> questionnaires = questionnaireService.getAllQuestionnaires();

        assertNotNull(questionnaires);
        assertEquals(2, questionnaires.size(), "The list should contain 2 questionnaires");
        verify(questionnaireRepository, times(1)).findAll();
    }

    /**
     * Tests the retrieval of a questionnaire by ID when the questionnaire exists in
     * the repository.
     *
     * @return void
     */
    @Test
    void testGetQuestionnaireByIdFound() {
        Questionnaire mockQuestionnaire = new Questionnaire();
        mockQuestionnaire.setId(1L);

        when(questionnaireRepository.findById(1L)).thenReturn(Optional.of(mockQuestionnaire));

        Optional<Questionnaire> questionnaire = questionnaireService.getQuestionnaireById(1L);

        assertTrue(questionnaire.isPresent(), "Questionnaire should be found");
        assertEquals(1L, questionnaire.get().getId());
        verify(questionnaireRepository, times(1)).findById(1L);
    }

    /**
     * Tests the retrieval of a questionnaire by ID when the questionnaire does not
     * exist in the repository.
     * 
     * @return void
     */
    @Test
    void testGetQuestionnaireByIdNotFound() {
        when(questionnaireRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Questionnaire> questionnaire = questionnaireService.getQuestionnaireById(1L);

        assertFalse(questionnaire.isPresent(), "Questionnaire should not be found");
        verify(questionnaireRepository, times(1)).findById(1L);
    }

    /**
     * Tests the retrieval of questionnaires by title.
     *
     * This test verifies that when a title is provided, the service returns a list
     * of questionnaires
     * whose titles contain the specified title.
     *
     * @return void
     */
    @Test
    void testSearchQuestionnairesByTitle() {
        Questionnaire questionnaire1 = new Questionnaire();
        questionnaire1.setTitle("Lymphoedema Awareness");

        Questionnaire questionnaire2 = new Questionnaire();
        questionnaire2.setTitle("Awareness Campaign");

        when(questionnaireRepository.findByTitleContainingIgnoreCase("awareness"))
                .thenReturn(List.of(questionnaire1, questionnaire2));

        List<Questionnaire> result = questionnaireService.searchQuestionnairesByTitle("awareness");

        assertEquals(2, result.size(), "Should return 2 questionnaires");
        assertTrue(result.get(0).getTitle().contains("Awareness"));
        assertTrue(result.get(1).getTitle().contains("Awareness"));
        verify(questionnaireRepository, times(1)).findByTitleContainingIgnoreCase("awareness");
    }

    /**
     * Tests the creation or update of a questionnaire.
     *
     * @return void
     */
    @Test
    void testCreateOrUpdateQuestionnaire() {
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setTitle("Lymphoedema Awareness");

        when(questionnaireRepository.save(any(Questionnaire.class))).thenReturn(questionnaire);

        Questionnaire savedQuestionnaire = questionnaireService.createOrUpdateQuestionnaire(questionnaire);

        assertNotNull(savedQuestionnaire);
        assertEquals("Lymphoedema Awareness", savedQuestionnaire.getTitle());
        verify(questionnaireRepository, times(1)).save(any(Questionnaire.class));
    }

    /**
     * Tests the deletion of a questionnaire by ID.
     * 
     * @return void
     */
    @Test
    void testDeleteQuestionnaire() {
        doNothing().when(questionnaireRepository).deleteById(1L);

        questionnaireService.deleteQuestionnaire(1L);

        verify(questionnaireRepository, times(1)).deleteById(1L);
    }

    /**
     * Tests the deletion of a questionnaire by ID when the questionnaire is not
     * found.
     * 
     * @return void
     */
    @Test
    void testDeleteQuestionnaireNotFound() {
        doThrow(new IllegalArgumentException("Questionnaire not found")).when(questionnaireRepository).deleteById(1L);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            questionnaireService.deleteQuestionnaire(1L);
        });

        assertEquals("Questionnaire not found", exception.getMessage());
        verify(questionnaireRepository, times(1)).deleteById(1L);
    }
}
