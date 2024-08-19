package uk.ac.cf.spring.nhs.Question.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import uk.ac.cf.spring.nhs.Question.Model.Question;

import java.util.List;

public interface JpaQuestionRepo extends JpaRepository<Question, Long> {
    // Find all questions by a specific questionnaire ID
    List<Question> findByQuestionnaireId(Long questionnaireId);

    // Find all questions by a specific category
    List<Question> findByQuestionCategory(String questionCategory);

    // Find all questions by a specific response type
    List<Question> findByQuestionResponseType(String questionResponseType);

}
