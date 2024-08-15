package uk.ac.cf.spring.nhs.UserQuestionnaire.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.ac.cf.spring.nhs.UserQuestionnaire.Model.UserQuestionnaire;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaUserQuestionnaireRepository extends JpaRepository<UserQuestionnaire, Long> {

    // Find all questionnaires assigned to a specific user
    List<UserQuestionnaire> findByUserID(String userID);

    // Find all completed questionnaires for a specific user
    List<UserQuestionnaire> findByUserIDAndQuestionnaireIsCompletedTrue(String userID);

    // Find all incomplete questionnaires for a specific user
    List<UserQuestionnaire> findByUserIDAndQuestionnaireIsCompletedFalse(String userID);

    // Find a specific UserQuestionnaire by userID and questionnaireID
    Optional<UserQuestionnaire> findByUserIDAndQuestionnaire_Id(String userID, Long id);

}
