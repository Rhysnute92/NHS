package uk.ac.cf.spring.nhs.Questionnaire.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.ac.cf.spring.nhs.Questionnaire.Model.Questionnaire;
import uk.ac.cf.spring.nhs.Questionnaire.Repository.JpaQuestionnaireRepo;

@Service
public class QuestionnaireService {

    @Autowired
    private JpaQuestionnaireRepo questionnaireRepository;

    /**
     * Retrieves all questionnaires from the repository.
     *
     * @return A list of all questionnaires.
     */
    public List<Questionnaire> getAllQuestionnaires() {
        return questionnaireRepository.findAll();
    }

    /**
     * Retrieves a questionnaire by its unique identifier.
     *
     * @param id the unique identifier of the questionnaire
     * @return an Optional containing the questionnaire if found, otherwise an empty
     *         Optional
     */
    public Optional<Questionnaire> getQuestionnaireById(Long id) {
        return questionnaireRepository.findById(id);
    }

    /**
     * Searches for questionnaires by title in a case-insensitive manner.
     *
     * @param title the title to search for
     * @return a list of questionnaires with matching titles
     */
    public List<Questionnaire> searchQuestionnairesByTitle(String title) {
        return questionnaireRepository.findByTitleContainingIgnoreCase(title);
    }

    /**
     * Creates or updates a questionnaire in the repository.
     *
     * @param questionnaire the questionnaire to create or update
     * @return the created or updated questionnaire
     */
    public Questionnaire createOrUpdateQuestionnaire(Questionnaire questionnaire) {
        return questionnaireRepository.save(questionnaire);
    }

    /**
     * Deletes a questionnaire from the repository by its unique identifier.
     *
     * @param id the unique identifier of the questionnaire to be deleted
     */
    public void deleteQuestionnaire(Long id) {
        questionnaireRepository.deleteById(id);
    }
}
