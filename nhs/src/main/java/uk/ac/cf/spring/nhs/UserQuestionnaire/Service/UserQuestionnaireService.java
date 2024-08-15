package uk.ac.cf.spring.nhs.UserQuestionnaire.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.ac.cf.spring.nhs.UserQuestionnaire.Model.UserQuestionnaire;
import uk.ac.cf.spring.nhs.UserQuestionnaire.Repository.JpaUserQuestionnaireRepository;

@Service
public class UserQuestionnaireService {

    @Autowired
    private JpaUserQuestionnaireRepository userQuestionnaireRepository;

    /**
     * Retrieves a list of user questionnaires for a specific user.
     *
     * @param userID the ID of the user
     * @return a list of user questionnaires
     */
    public List<UserQuestionnaire> getUserQuestionnaires(String userID) {
        return userQuestionnaireRepository.findByUserID(userID);
    }

    /**
     * Retrieves a list of completed user questionnaires for a specific user.
     *
     * @param userID the unique identifier of the user
     * @return a list of completed user questionnaires
     */
    public List<UserQuestionnaire> getCompletedUserQuestionnaires(String userID) {
        return userQuestionnaireRepository.findByUserIDAndQuestionnaireIsCompletedTrue(userID);
    }

    /**
     * Retrieves a list of incomplete user questionnaires for a specific user.
     *
     * @param userID the ID of the user
     * @return a list of incomplete user questionnaires
     */
    public List<UserQuestionnaire> getIncompleteUserQuestionnaires(String userID) {
        return userQuestionnaireRepository.findByUserIDAndQuestionnaireIsCompletedFalse(userID);
    }

    /**
     * Retrieves a specific user questionnaire by user ID and questionnaire ID.
     *
     * @param userID          the ID of the user
     * @param questionnaireId the ID of the questionnaire
     * @return an optional user questionnaire
     */
    public Optional<UserQuestionnaire> getUserQuestionnaire(String userID, Long questionnaireId) {
        return userQuestionnaireRepository.findByUserIDAndQuestionnaire_QuestionnaireId(userID, questionnaireId);
    }

    /**
     * Saves a user questionnaire to the database.
     *
     * @param userQuestionnaire the user questionnaire to be saved
     * @return the saved user questionnaire
     */
    public UserQuestionnaire saveUserQuestionnaire(UserQuestionnaire userQuestionnaire) {
        return userQuestionnaireRepository.save(userQuestionnaire);
    }

    /**
     * Deletes a user questionnaire by its ID.
     *
     * @param userQuestionnaireId the ID of the user questionnaire to delete
     */
    public void deleteUserQuestionnaire(Long userQuestionnaireId) {
        userQuestionnaireRepository.deleteById(userQuestionnaireId);
    }
}
