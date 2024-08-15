/* package uk.ac.cf.spring.nhs.Questionnaire.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import uk.ac.cf.spring.nhs.Questionnaire.Model.Questionnaire;
import uk.ac.cf.spring.nhs.Questionnaire.Service.QuestionnaireService;
import uk.ac.cf.spring.nhs.Security.AuthenticationInterface;
import uk.ac.cf.spring.nhs.Security.CustomUserDetails;
import uk.ac.cf.spring.nhs.UserQuestionnaire.Model.UserQuestionnaire;
import uk.ac.cf.spring.nhs.UserQuestionnaire.Service.UserQuestionnaireService;

@Controller
public class QuestionnaireFormController {

    @Autowired
    private QuestionnaireService questionnaireService;

    @Autowired
    private AuthenticationInterface authenticationFacade;

    @Autowired
    private UserQuestionnaireService userQuestionnaireService;

    @GetMapping("/questionnaire/{id}")
    public String getQuestionnairePage(@PathVariable Long id, Model model) {
        Optional<Questionnaire> questionnaire = questionnaireService.getQuestionnaireById(id);
        if (questionnaire.isPresent()) {
            model.addAttribute("questionnaire", questionnaire.get());

            Object principal = authenticationFacade.getAuthentication().getPrincipal();
            Long userId = ((CustomUserDetails) principal).getUserId();
            UserQuestionnaire userQuestionnaire = userQuestionnaireService.getUserQuestionnaire(userId, id)
                    .orElseThrow(() -> new ResourceNotFoundException("User Questionnaire not found"));

            // Mark the questionnaire as in progress
            userQuestionnaire.setQuestionnaireInProgress(true);
            userQuestionnaireService.saveUserQuestionnaire(userQuestionnaire);

            return "questionnaire"; // Thymeleaf template name
        } else {
            return "error/404"; // Handle not found
        }
    }
}
 */