package uk.ac.cf.spring.nhs.UserQuestion.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.ac.cf.spring.nhs.Question.Model.Question;
import uk.ac.cf.spring.nhs.Security.AuthenticationInterface;
import uk.ac.cf.spring.nhs.Security.CustomUserDetails;
import uk.ac.cf.spring.nhs.UserQuestion.Model.UserQuestion;
import uk.ac.cf.spring.nhs.UserQuestion.Repository.JpaUserQuestionRepository;
import uk.ac.cf.spring.nhs.UserQuestionnaire.Model.UserQuestionnaire;
import uk.ac.cf.spring.nhs.UserQuestionnaire.Service.UserQuestionnaireService;

@Service
public class UserQuestionService {

    @Autowired
    private JpaUserQuestionRepository userQuestionRepository;

    @Autowired
    private UserQuestionnaireService userQuestionnaireService;

    @Autowired
    private AuthenticationInterface authenticationFacade;

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

    public void saveResponses(Long userQuestionnaireId, Map<String, String> responses) {
        // Fetch the UserQuestionnaire by its ID
        Optional<UserQuestionnaire> userQuestionnaireOpt = userQuestionnaireService
                .getUserQuestionnaireById(userQuestionnaireId);
        if (!userQuestionnaireOpt.isPresent()) {
            throw new IllegalArgumentException("Invalid user questionnaire ID");
        }
        UserQuestionnaire userQuestionnaire = userQuestionnaireOpt.get();

        // Save each response
        responses.forEach((questionIdStr, answer) -> {
            Long questionId = Long.parseLong(questionIdStr);
            UserQuestion userQuestion = new UserQuestion();
            userQuestion.setUserQuestionnaire(userQuestionnaire);

            Question question = new Question();
            question.setQuestionID(questionId);
            userQuestion.setQuestion(question);

            // Determine if the answer is a text or a score response
            try {
                Integer score = Integer.parseInt(answer);
                userQuestion.setUserResponseScore(score);
                userQuestion.setUserResponseText(null); // Ensure text is null for scores
            } catch (NumberFormatException e) {
                userQuestion.setUserResponseText(answer);
                userQuestion.setUserResponseScore(null); // Ensure score is null for text responses
            }

            userQuestion.setResponseDateTime(LocalDateTime.now());
            userQuestionRepository.save(userQuestion);
        });

        // Mark the questionnaire as completed
        userQuestionnaire.setQuestionnaireIsCompleted(true);
        userQuestionnaire.setQuestionnaireCompletionDate(LocalDateTime.now());

        // Use the service to save the completed questionnaire
        userQuestionnaireService.saveUserQuestionnaire(userQuestionnaire);
    }

    public void saveResponsesWithoutCompletion(Long userQuestionnaireId, Map<String, String> responses) {
        System.out.println("Saving responses for UserQuestionnaireID: " + userQuestionnaireId);
        System.out.println("Responses: " + responses);

        // Fetch the UserQuestionnaire directly by its ID
        Optional<UserQuestionnaire> userQuestionnaireOpt = userQuestionnaireService
                .getUserQuestionnaireById(userQuestionnaireId);
        if (!userQuestionnaireOpt.isPresent()) {
            throw new IllegalArgumentException("Invalid user questionnaire ID");
        }
        UserQuestionnaire userQuestionnaire = userQuestionnaireOpt.get();

        System.out.println("Fetched UserQuestionnaire: " + userQuestionnaire);

        responses.forEach((questionIdStr, answer) -> {
            Long questionId = Long.parseLong(questionIdStr);
            System.out.println("Processing QuestionID: " + questionId + " with answer: " + answer);

            UserQuestion userQuestion = new UserQuestion();
            userQuestion.setUserQuestionnaire(userQuestionnaire);

            Question question = new Question();
            question.setQuestionID(questionId);
            userQuestion.setQuestion(question);

            try {
                // Attempt to parse the answer as an integer (score)
                Integer score = Integer.parseInt(answer);
                userQuestion.setUserResponseScore(score);
                userQuestion.setUserResponseText(null); // Ensure text is null for scores
            } catch (NumberFormatException e) {
                // If parsing fails, treat it as a text response
                userQuestion.setUserResponseText(answer);
                userQuestion.setUserResponseScore(null); // Ensure score is null for text responses
            }

            userQuestion.setResponseDateTime(LocalDateTime.now());
            userQuestionRepository.save(userQuestion);

            System.out.println("Saved UserQuestion: " + userQuestion);
        });

        userQuestionnaireService.saveUserQuestionnaire(userQuestionnaire);
    }
}
