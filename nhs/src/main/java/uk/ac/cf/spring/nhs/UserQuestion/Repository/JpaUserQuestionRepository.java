package uk.ac.cf.spring.nhs.UserQuestion.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.ac.cf.spring.nhs.UserQuestion.Model.UserQuestion;

import java.util.List;

@Repository
public interface JpaUserQuestionRepository extends JpaRepository<UserQuestion, Long> {

    // Find all UserQuestions by UserQuestionnaire ID
    List<UserQuestion> findByUserQuestionnaire_UserQuestionnaireId(Long userQuestionnaireId);

    // Find all UserQuestions by Question ID
    List<UserQuestion> findByQuestion_QuestionID(Long questionId);

    // Find a specific UserQuestion by UserQuestionnaire ID and Question ID
    UserQuestion findByUserQuestionnaire_UserQuestionnaireIdAndQuestion_QuestionID(Long userQuestionnaireId,
            Long questionId);

}
