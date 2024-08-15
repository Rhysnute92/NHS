package uk.ac.cf.spring.nhs.Questionnaire.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.ac.cf.spring.nhs.Questionnaire.Model.Questionnaire;
import uk.ac.cf.spring.nhs.Questionnaire.Repository.JpaQuestionnaireRepo;

@Service
public class QuestionnaireService {

    @Autowired
    private JpaQuestionnaireRepo questionnaireRepository;

    public List<Questionnaire> getAllQuestionnaires() {
        return questionnaireRepository.findAll();
    }

    public Optional<Questionnaire> getQuestionnaireById(Long id) {
        return questionnaireRepository.findById(id);
    }

    public List<Questionnaire> searchQuestionnairesByTitle(String title) {
        return questionnaireRepository.findByTitleContainingIgnoreCase(title);
    }

    public Questionnaire createOrUpdateQuestionnaire(Questionnaire questionnaire) {
        return questionnaireRepository.save(questionnaire);
    }

    public void deleteQuestionnaire(Long id) {
        questionnaireRepository.deleteById(id);
    }
}
