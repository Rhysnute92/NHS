package uk.ac.cf.spring.nhs.UserQuestionnaire.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.ac.cf.spring.nhs.UserQuestionnaire.Model.UserQuestionnaire;
import uk.ac.cf.spring.nhs.UserQuestionnaire.Service.UserQuestionnaireService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/userQuestionnaires")
public class UserQuestionnaireController {

    @Autowired
    private UserQuestionnaireService userQuestionnaireService;

    /**
     * Retrieves a list of user questionnaires for a specific user.
     *
     * @param userID the ID of the user
     * @return a list of user questionnaires
     */
    @GetMapping("/user/{userID}")
    public ResponseEntity<List<UserQuestionnaire>> getUserQuestionnaires(@PathVariable Long userID) {
        List<UserQuestionnaire> userQuestionnaires = userQuestionnaireService.getUserQuestionnaires(userID);
        return ResponseEntity.ok(userQuestionnaires);
    }

    /**
     * Retrieves a list of completed user questionnaires for a specific user.
     *
     * @param userID the ID of the user
     * @return a list of completed user questionnaires
     */
    @GetMapping("/user/{userID}/completed")
    public ResponseEntity<List<UserQuestionnaire>> getCompletedUserQuestionnaires(@PathVariable Long userID) {
        List<UserQuestionnaire> userQuestionnaires = userQuestionnaireService.getCompletedUserQuestionnaires(userID);
        return ResponseEntity.ok(userQuestionnaires);
    }

    /**
     * Retrieves a list of incomplete user questionnaires for a specific user.
     *
     * @param userID the ID of the user
     * @return a ResponseEntity containing a list of incomplete user questionnaires
     */
    @GetMapping("/user/{userID}/incomplete")
    public ResponseEntity<List<UserQuestionnaire>> getIncompleteUserQuestionnaires(@PathVariable Long userID) {
        List<UserQuestionnaire> userQuestionnaires = userQuestionnaireService.getIncompleteUserQuestionnaires(userID);
        return ResponseEntity.ok(userQuestionnaires);
    }

    /**
     * Retrieves a user questionnaire for a specific user and questionnaire ID.
     *
     * @param userID          the ID of the user
     * @param questionnaireId the ID of the questionnaire
     * @return a ResponseEntity containing the user questionnaire, or a 404 response
     *         if not found
     */
    @GetMapping("/user/{userID}/questionnaire/{questionnaireId}")
    public ResponseEntity<UserQuestionnaire> getUserQuestionnaire(@PathVariable Long userID,
            @PathVariable Long questionnaireId) {
        Optional<UserQuestionnaire> userQuestionnaire = userQuestionnaireService.getUserQuestionnaire(userID,
                questionnaireId);
        return userQuestionnaire.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Creates a new user questionnaire.
     *
     * @param userQuestionnaire the user questionnaire to be created
     * @return the created user questionnaire
     */
    @PostMapping
    public ResponseEntity<UserQuestionnaire> createUserQuestionnaire(@RequestBody UserQuestionnaire userQuestionnaire) {
        UserQuestionnaire savedUserQuestionnaire = userQuestionnaireService.saveUserQuestionnaire(userQuestionnaire);
        return ResponseEntity.ok(savedUserQuestionnaire);
    }

    /**
     * Updates an existing user questionnaire.
     *
     * @param id                the ID of the user questionnaire to update
     * @param userQuestionnaire the updated user questionnaire
     * @return the updated user questionnaire, or a 404 response if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserQuestionnaire> updateUserQuestionnaire(@PathVariable Long id,
            @RequestBody UserQuestionnaire userQuestionnaire) {
        Optional<UserQuestionnaire> existingUserQuestionnaire = userQuestionnaireService
                .getUserQuestionnaire(userQuestionnaire.getUserID(), id);
        if (existingUserQuestionnaire.isPresent()) {
            userQuestionnaire.setUserQuestionnaireId(id);
            UserQuestionnaire updatedUserQuestionnaire = userQuestionnaireService
                    .saveUserQuestionnaire(userQuestionnaire);
            return ResponseEntity.ok(updatedUserQuestionnaire);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a user questionnaire by its ID.
     *
     * @param id the ID of the user questionnaire to delete
     * @return a ResponseEntity indicating the result of the deletion
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserQuestionnaire(@PathVariable Long id) {
        Optional<UserQuestionnaire> existingUserQuestionnaire = userQuestionnaireService.getUserQuestionnaire(null, id);
        if (existingUserQuestionnaire.isPresent()) {
            userQuestionnaireService.deleteUserQuestionnaire(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
