package uk.ac.cf.spring.nhs.Question.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.ac.cf.spring.nhs.Question.Model.Question;
import uk.ac.cf.spring.nhs.Question.Service.QuestionService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping
    public ResponseEntity<List<Question>> getAllQuestions() {
        List<Question> questions = questionService.getQuestionsByQuestionnaireId(null);
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/questionnaire/{questionnaireId}")
    public ResponseEntity<List<Question>> getQuestionsByQuestionnaireId(@PathVariable Long questionnaireId) {
        List<Question> questions = questionService.getQuestionsByQuestionnaireId(questionnaireId);
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category) {
        List<Question> questions = questionService.getQuestionsByCategory(category);
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/responseType/{responseType}")
    public ResponseEntity<List<Question>> getQuestionsByResponseType(@PathVariable String responseType) {
        List<Question> questions = questionService.getQuestionsByResponseType(responseType);
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long id) {
        Optional<Question> question = questionService.getQuestionsByQuestionnaireId(id)
                .stream().findFirst();
        return question.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Question> createQuestion(@RequestBody Question question) {
        Question savedQuestion = questionService.saveQuestion(question);
        return ResponseEntity.ok(savedQuestion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id, @RequestBody Question question) {
        Optional<Question> existingQuestion = questionService.getQuestionsByQuestionnaireId(id)
                .stream().findFirst();
        if (existingQuestion.isPresent()) {
            question.setQuestionID(id);
            Question updatedQuestion = questionService.saveQuestion(question);
            return ResponseEntity.ok(updatedQuestion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        Optional<Question> existingQuestion = questionService.getQuestionsByQuestionnaireId(id)
                .stream().findFirst();
        if (existingQuestion.isPresent()) {
            questionService.deleteQuestion(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
