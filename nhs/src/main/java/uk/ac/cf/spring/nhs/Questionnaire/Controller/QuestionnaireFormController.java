package uk.ac.cf.spring.nhs.Questionnaire.Controller;

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

    /**
     * Handles HTTP GET requests to retrieve a questionnaire page by its unique
     * identifier.
     *
     * @param id    the unique identifier of the questionnaire
     * @param model the Spring MVC model object to store the questionnaire data
     * @return the name of the view to render, either "questionnaire" or "error/404"
     */
    @GetMapping("/questionnaire/{id}")
    public String getQuestionnairePage(@PathVariable Long id, Model model) {
        Optional<Questionnaire> questionnaire = questionnaireService.getQuestionnaireById(id);
        if (questionnaire.isPresent()) {
            model.addAttribute("questionnaire", questionnaire.get());

            Object principal = authenticationFacade.getAuthentication().getPrincipal();
            Long userId = ((CustomUserDetails) principal).getUserId();
            Optional<UserQuestionnaire> userQuestionnaireOpt = userQuestionnaireService.getUserQuestionnaire(userId,
                    id);

            if (userQuestionnaireOpt.isPresent()) {
                UserQuestionnaire userQuestionnaire = userQuestionnaireOpt.get();
                userQuestionnaire.setQuestionnaireInProgress(true);
                userQuestionnaireService.saveUserQuestionnaire(userQuestionnaire);
                return "questionnaire/questionnaire";
            } else {
                return "error/404";
            }
        } else {
            return "error/404";
        }
    }

}
