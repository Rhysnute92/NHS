package uk.ac.cf.spring.nhs.UserQuestion.Service;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.ac.cf.spring.nhs.AddPatient.Service.EmailService;
import uk.ac.cf.spring.nhs.Question.Model.Question;
import uk.ac.cf.spring.nhs.Security.AuthenticationInterface;
import uk.ac.cf.spring.nhs.Security.CustomUserDetails;
import uk.ac.cf.spring.nhs.UserQuestion.Model.UserQuestion;
import uk.ac.cf.spring.nhs.UserQuestion.Repository.JpaUserQuestionRepository;
import uk.ac.cf.spring.nhs.UserQuestionnaire.Model.UserQuestionnaire;
import uk.ac.cf.spring.nhs.UserQuestionnaire.Service.PDFService;
import uk.ac.cf.spring.nhs.UserQuestionnaire.Service.UserQuestionnaireService;

@Service
public class UserQuestionService {

    @Autowired
    private JpaUserQuestionRepository userQuestionRepository;

    @Autowired
    private UserQuestionnaireService userQuestionnaireService;

    @Autowired
    private AuthenticationInterface authenticationFacade;
    @Autowired
    private PDFService pdfService;
    @Autowired
    private EmailService emailService;

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

    /**
     * Saves user responses for a given questionnaire.
     *
     * @param userQuestionnaireId the ID of the user questionnaire being completed
     * @param responses           a map of question IDs to user responses
     */
    public void saveResponses(Long userQuestionnaireId, Map<String, String> responses) {
        // Fetch the UserQuestionnaire using the service
        Object principal = authenticationFacade.getAuthentication().getPrincipal();
        Long userId = ((CustomUserDetails) principal).getUserId();
        Optional<UserQuestionnaire> userQuestionnaireOpt = userQuestionnaireService
                .getUserQuestionnaire(userId, userQuestionnaireId);
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
            } catch (NumberFormatException e) {
                userQuestion.setUserResponseText(answer);
            }

            userQuestion.setResponseDateTime(LocalDateTime.now());
            userQuestionRepository.save(userQuestion);
        });

        // Mark the questionnaire as completed
        userQuestionnaire.setQuestionnaireIsCompleted(true);
        userQuestionnaire.setQuestionnaireCompletionDate(LocalDateTime.now());
        userQuestionnaireService.saveUserQuestionnaire(userQuestionnaire); // Use the service to save

        // Generate the PDF
        List<UserQuestion> userQuestions = userQuestionRepository.findByUserQuestionnaire_UserQuestionnaireId(userQuestionnaireId);
        byte[] pdfBytes = pdfService.generatePDF(userQuestions);

        // Send email with the PDF as an attachment
        try {
            emailService.sendEmailWithQuestionnaire(
                    "derbyandburton.mscproject@gmail.com",
                    "Completed Questionnaire",
                    "Please find attached a completed questionnaire.",
                    pdfBytes
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Saves user responses for a given questionnaire without marking it as complete.
     *
     * @param userQuestionnaireId the ID of the user questionnaire being saved
     * @param responses           a map of question IDs to user responses
     */
    public void saveResponsesWithoutCompletion(Long userQuestionnaireId, Map<String, String> responses) {
        Object principal = authenticationFacade.getAuthentication().getPrincipal();
        Long userId = ((CustomUserDetails) principal).getUserId();
        Optional<UserQuestionnaire> userQuestionnaireOpt = userQuestionnaireService
                .getUserQuestionnaire(userId, userQuestionnaireId);
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
            } catch (NumberFormatException e) {
                userQuestion.setUserResponseText(answer);
            }

            userQuestion.setResponseDateTime(LocalDateTime.now());
            userQuestionRepository.save(userQuestion);
        });

        // Do not mark the questionnaire as completed
        userQuestionnaireService.saveUserQuestionnaire(userQuestionnaire);
    }
}
