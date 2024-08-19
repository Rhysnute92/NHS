package uk.ac.cf.spring.nhs.Questionnaire.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uk.ac.cf.spring.nhs.Questionnaire.Model.Questionnaire;

@Repository
public interface JpaQuestionnaireRepo extends JpaRepository<Questionnaire, Long> {

    // Custom query method to find questionnaires by title
    List<Questionnaire> findByTitleContainingIgnoreCase(String title);
}
