package uk.ac.cf.spring.nhs.UserQuestionnaire.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserQuestionnaireHistoricalController {

    @GetMapping("/questionnaire/historical/user")
    public String showHistoricalQuestionnaireUser() {
        return "questionnaire/historicalQuestionnaire";
    }

    @GetMapping("/questionnaire/historical/provider")
    public String showHistoricalQuestionnaireProvider() {
        return "questionnaire/providerHistoricalQuestionnaire";
    }
}
