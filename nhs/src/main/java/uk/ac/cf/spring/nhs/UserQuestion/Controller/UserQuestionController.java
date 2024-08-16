package uk.ac.cf.spring.nhs.UserQuestion.Controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uk.ac.cf.spring.nhs.UserQuestion.Model.UserQuestion;
import uk.ac.cf.spring.nhs.UserQuestion.Service.UserQuestionService;

@RestController
@RequestMapping("/api/userQuestions")
public class UserQuestionController {

    @Autowired
    private UserQuestionService userQuestionService;

    /**
     * Retrieves a list of UserQuestions associated with a specific
     * UserQuestionnaire.
     *
     * @param userQuestionnaireId the unique identifier of the UserQuestionnaire
     * @return a list of UserQuestions linked to the specified UserQuestionnaire
     */
    @GetMapping("/userQuestionnaire/{userQuestionnaireId}")
    public ResponseEntity<List<UserQuestion>> getUserQuestionsByUserQuestionnaireId(
            @PathVariable Long userQuestionnaireId) {
        List<UserQuestion> userQuestions = userQuestionService
                .getUserQuestionsByUserQuestionnaireId(userQuestionnaireId);
        return ResponseEntity.ok(userQuestions);
    }

    /**
     * Retrieves a list of UserQuestions associated with a specific question ID.
     *
     * @param questionId the unique identifier of the question
     * @return a ResponseEntity containing a list of UserQuestions linked to the
     *         specified question ID
     */
    @GetMapping("/question/{questionId}")
    public ResponseEntity<List<UserQuestion>> getUserQuestionsByQuestionId(@PathVariable Long questionId) {
        List<UserQuestion> userQuestions = userQuestionService.getUserQuestionsByQuestionId(questionId);
        return ResponseEntity.ok(userQuestions);
    }

    /**
     * Retrieves a UserQuestion by its associated UserQuestionnaire ID and question
     * ID.
     *
     * @param userQuestionnaireId the unique identifier of the UserQuestionnaire
     * @param questionId          the unique identifier of the question
     * @return the UserQuestion object if found, otherwise a not found response
     */
    @GetMapping("/userQuestionnaire/{userQuestionnaireId}/question/{questionId}")
    public ResponseEntity<UserQuestion> getUserQuestion(@PathVariable Long userQuestionnaireId,
            @PathVariable Long questionId) {
        Optional<UserQuestion> userQuestion = userQuestionService.getUserQuestion(userQuestionnaireId, questionId);
        return userQuestion.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Creates a new UserQuestion and saves it to the database.
     *
     * @param userQuestion the UserQuestion object to be saved
     * @return the saved UserQuestion object
     */
    @PostMapping
    public ResponseEntity<UserQuestion> createUserQuestion(@RequestBody UserQuestion userQuestion) {
        UserQuestion savedUserQuestion = userQuestionService.saveUserQuestion(userQuestion);
        return ResponseEntity.ok(savedUserQuestion);
    }

    /**
     * Updates a UserQuestion with the specified ID.
     *
     * @param id           the unique identifier of the UserQuestion to be updated
     * @param userQuestion the updated UserQuestion object
     * @return the updated UserQuestion object if found, otherwise a not found
     *         response
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserQuestion> updateUserQuestion(@PathVariable Long id,
            @RequestBody UserQuestion userQuestion) {
        Optional<UserQuestion> existingUserQuestion = userQuestionService.getUserQuestion(
                userQuestion.getUserQuestionnaire().getUserQuestionnaireId(),
                userQuestion.getQuestion().getQuestionID());
        if (existingUserQuestion.isPresent()) {
            userQuestion.setUserQuestionID(id);
            UserQuestion updatedUserQuestion = userQuestionService.saveUserQuestion(userQuestion);
            return ResponseEntity.ok(updatedUserQuestion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a UserQuestion by its unique identifier.
     *
     * @param id the unique identifier of the UserQuestion to be deleted
     * @return a no content response if the UserQuestion is found and deleted,
     *         otherwise a not found response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserQuestion(@PathVariable Long id) {
        Optional<UserQuestion> existingUserQuestion = userQuestionService.getUserQuestion(null, id);
        if (existingUserQuestion.isPresent()) {
            userQuestionService.deleteUserQuestion(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Handles the submission of user responses for a specific questionnaire.
     *
     * @param questionnaireId the ID of the questionnaire being completed
     * @param responses       a map of question IDs to user responses
     * @return a ResponseEntity indicating the result of the operation
     */
    @PostMapping("/submit/{questionnaireId}")
    public ResponseEntity<?> submitUserQuestions(
            @PathVariable Long questionnaireId,
            @RequestBody Map<String, String> responses) {
        try {
            userQuestionService.saveResponses(questionnaireId, responses);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving responses");
        }
    }
}