package uk.ac.cf.spring.nhs.Questionnaire.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uk.ac.cf.spring.nhs.Questionnaire.Model.Questionnaire;
import uk.ac.cf.spring.nhs.Questionnaire.Service.QuestionnaireService;

@RestController
@RequestMapping("/api/questionnaires")
public class QuestionnaireController {

    @Autowired
    private QuestionnaireService questionnaireService;

    @GetMapping
    public ResponseEntity<List<Questionnaire>> getAllQuestionnaires() {
        List<Questionnaire> questionnaires = questionnaireService.getAllQuestionnaires();
        return ResponseEntity.ok(questionnaires);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Questionnaire> getQuestionnaireById(@PathVariable Long id) {
        Optional<Questionnaire> questionnaire = questionnaireService.getQuestionnaireById(id);
        return questionnaire.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Questionnaire>> searchQuestionnairesByTitle(@RequestParam String title) {
        List<Questionnaire> result = questionnaireService.searchQuestionnairesByTitle(title);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<Questionnaire> createQuestionnaire(@RequestBody Questionnaire questionnaire) {
        Questionnaire createdQuestionnaire = questionnaireService.createOrUpdateQuestionnaire(questionnaire);
        return ResponseEntity.ok(createdQuestionnaire);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Questionnaire> updateQuestionnaire(@PathVariable Long id,
            @RequestBody Questionnaire questionnaire) {
        Optional<Questionnaire> existingQuestionnaire = questionnaireService.getQuestionnaireById(id);
        if (existingQuestionnaire.isPresent()) {
            questionnaire.setId(id);
            Questionnaire updatedQuestionnaire = questionnaireService.createOrUpdateQuestionnaire(questionnaire);
            return ResponseEntity.ok(updatedQuestionnaire);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestionnaire(@PathVariable Long id) {
        Optional<Questionnaire> existingQuestionnaire = questionnaireService.getQuestionnaireById(id);
        if (existingQuestionnaire.isPresent()) {
            questionnaireService.deleteQuestionnaire(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
