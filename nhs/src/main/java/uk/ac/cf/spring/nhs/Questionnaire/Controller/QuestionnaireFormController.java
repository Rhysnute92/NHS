package uk.ac.cf.spring.nhs.Questionnaire.Controller;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import uk.ac.cf.spring.nhs.Questionnaire.Model.Questionnaire;
import uk.ac.cf.spring.nhs.Questionnaire.Service.QuestionnaireService;
import uk.ac.cf.spring.nhs.Security.AuthenticationInterface;
import uk.ac.cf.spring.nhs.Security.CustomUserDetails;
import uk.ac.cf.spring.nhs.UserQuestion.Model.UserQuestion;
import uk.ac.cf.spring.nhs.UserQuestion.Service.UserQuestionService;
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

    @Autowired
    private UserQuestionService userQuestionService;

    @GetMapping("/questionnaire/{userQuestionnaireId}")
    public String getQuestionnairePage(
            @PathVariable Long userQuestionnaireId,
            @RequestParam("questionnaireId") Long questionnaireId,
            Model model) {

        // Retrieve the authenticated user's ID
        Object principal = authenticationFacade.getAuthentication().getPrincipal();
        Long userId = ((CustomUserDetails) principal).getUserId();

        // Fetch the UserQuestionnaire by its ID
        Optional<UserQuestionnaire> userQuestionnaireOpt = userQuestionnaireService
                .getUserQuestionnaireById(userQuestionnaireId);

        if (userQuestionnaireOpt.isPresent()) {
            UserQuestionnaire userQuestionnaire = userQuestionnaireOpt.get();

            // Ensure the UserQuestionnaire belongs to the authenticated user and matches
            // the questionnaireId
            if (userQuestionnaire.getUserID().equals(userId)
                    && userQuestionnaire.getQuestionnaire().getId().equals(questionnaireId)) {
                model.addAttribute("questionnaire", userQuestionnaire.getQuestionnaire());

                // Check if the questionnaireStartDate is null and set it to now if it is
                if (userQuestionnaire.getQuestionnaireStartDate() == null) {
                    userQuestionnaire.setQuestionnaireStartDate(LocalDateTime.now());
                }

                // Mark the questionnaire as in progress
                userQuestionnaire.setQuestionnaireInProgress(true);
                userQuestionnaireService.saveUserQuestionnaire(userQuestionnaire);

                return "questionnaire/questionnaire";
            } else {
                return "error/404"; // Unauthorized access or mismatched questionnaire
            }
        } else {
            return "error/404"; // UserQuestionnaire not found
        }
    }

    @GetMapping("/questionnaire/{questionnaireId}/details")
    public String showQuestionnaireDetails(@PathVariable Long questionnaireId, Model model) {
        Optional<Questionnaire> questionnaireOpt = questionnaireService.getQuestionnaireById(questionnaireId);

        if (questionnaireOpt.isPresent()) {
            Questionnaire questionnaire = questionnaireOpt.get();
            List<UserQuestion> responses = userQuestionService.getUserQuestionsByUserQuestionnaireId(questionnaireId);

            // Group responses by category
            Map<String, List<UserQuestion>> groupedResponses = responses.stream()
                    .collect(Collectors.groupingBy(r -> r.getQuestion().getQuestionCategory(),
                            Collectors.collectingAndThen(Collectors.toList(),
                                    list -> list.isEmpty() ? Collections.emptyList() : list)));

            model.addAttribute("questionnaire", questionnaire);
            model.addAttribute("groupedResponses", groupedResponses);
            System.out.println(groupedResponses);
            return "questionnaire/questionnaireDetails";
        } else {
            return "error/404";
        }
    }

}
