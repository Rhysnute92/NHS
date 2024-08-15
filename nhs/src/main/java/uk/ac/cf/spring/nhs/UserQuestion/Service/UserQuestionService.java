package uk.ac.cf.spring.nhs.UserQuestion.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.ac.cf.spring.nhs.UserQuestion.Model.UserQuestion;
import uk.ac.cf.spring.nhs.UserQuestion.Repository.JpaUserQuestionRepository;

@Service
public class UserQuestionService {

    @Autowired
    private JpaUserQuestionRepository userQuestionRepository;

    /**
     * Retrieves a list of UserQuestions associated with a specific
     * UserQuestionnaire.
     *
     * @param userQuestionnaireId the unique identifier of the UserQuestionnaire
     * @return a list of UserQuestions linked to the specified UserQuestionnaire
     */
    public List<UserQuestion> getUserQuestionsByUserQuestionnaireId(Long userQuestionnaireId) {
        return userQuestionRepository.findByUserQuestionnaire_UserQuestionnaireId(userQuestionnaireId);
    }

    /**
     * Retrieves a list of user questions associated with a specific question ID.
     *
     * @param questionId the unique identifier of the question
     * @return a list of user questions linked to the specified question ID
     */
    public List<UserQuestion> getUserQuestionsByQuestionId(Long questionId) {
        return userQuestionRepository.findByQuestion_QuestionID(questionId);
    }

    /**
     * Retrieves a UserQuestion entity based on the provided UserQuestionnaire ID
     * and Question ID.
     *
     * @param userQuestionnaireId the ID of the UserQuestionnaire
     * @param questionId          the ID of the Question
     * @return an Optional containing the UserQuestion entity if found, otherwise an
     *         empty Optional
     */
    public Optional<UserQuestion> getUserQuestion(Long userQuestionnaireId, Long questionId) {
        return Optional.ofNullable(userQuestionRepository
                .findByUserQuestionnaire_UserQuestionnaireIdAndQuestion_QuestionID(userQuestionnaireId, questionId));
    }

    /**
     * Saves a UserQuestion entity to the repository.
     *
     * @param userQuestion the UserQuestion entity to be saved
     * @return the saved UserQuestion entity
     */
    public UserQuestion saveUserQuestion(UserQuestion userQuestion) {
        return userQuestionRepository.save(userQuestion);
    }

    /**
     * Deletes a UserQuestion entity from the repository based on the provided
     * UserQuestion ID.
     *
     * @param userQuestionId the ID of the UserQuestion to be deleted
     */
    public void deleteUserQuestion(Long userQuestionId) {
        userQuestionRepository.deleteById(userQuestionId);
    }
}
