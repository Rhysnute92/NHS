package uk.ac.cf.spring.nhs.UserQuestionnaire.Model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import uk.ac.cf.spring.nhs.Questionnaire.Model.Questionnaire;
import uk.ac.cf.spring.nhs.Security.UserCredentials.UserCredentials;

@Entity
@Table(name = "UserQuestionnaires")
public class UserQuestionnaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_questionnaire_id")
    private Long userQuestionnaireId;

    @ManyToOne
    @JoinColumn(name = "questionnaire_id", nullable = false)
    private Questionnaire questionnaire;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserCredentials user;

    @Column(name = "questionnaire_start_date", nullable = false)
    private LocalDateTime questionnaireStartDate;

    @Column(name = "questionnaire_is_completed", nullable = false)
    private Boolean questionnaireIsCompleted = false;

    @Column(name = "questionnaire_completion_date")
    private LocalDateTime questionnaireCompletionDate;

    // Getters and Setters

    /**
     * @return the userQuestionnaireId
     */
    public Long getUserQuestionnaireId() {
        return userQuestionnaireId;
    }

    /**
     * @param userQuestionnaireId the userQuestionnaireId to set
     */
    public void setUserQuestionnaireId(Long userQuestionnaireId) {
        this.userQuestionnaireId = userQuestionnaireId;
    }

    /**
     * @return the questionnaire
     */
    public Questionnaire getQuestionnaire() {
        return questionnaire;
    }

    /**
     * @param questionnaire the questionnaire to set
     */
    public void setQuestionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }

    /**
     * @return the user
     */
    public UserCredentials getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(UserCredentials user) {
        this.user = user;
    }

    /**
     * @return the questionnaireStartDate
     */
    public LocalDateTime getQuestionnaireStartDate() {
        return questionnaireStartDate;
    }

    /**
     * @param questionnaireStartDate the questionnaireStartDate to set
     */
    public void setQuestionnaireStartDate(LocalDateTime questionnaireStartDate) {
        this.questionnaireStartDate = questionnaireStartDate;
    }

    /**
     * @return the questionnaireIsCompleted
     */
    public Boolean getQuestionnaireIsCompleted() {
        return questionnaireIsCompleted;
    }

    /**
     * @param questionnaireIsCompleted the questionnaireIsCompleted to set
     */
    public void setQuestionnaireIsCompleted(Boolean questionnaireIsCompleted) {
        this.questionnaireIsCompleted = questionnaireIsCompleted;
    }

    /**
     * @return the questionnaireCompletionDate
     */
    public LocalDateTime getQuestionnaireCompletionDate() {
        return questionnaireCompletionDate;
    }

    /**
     * @param questionnaireCompletionDate the questionnaireCompletionDate to set
     */
    public void setQuestionnaireCompletionDate(LocalDateTime questionnaireCompletionDate) {
        this.questionnaireCompletionDate = questionnaireCompletionDate;
    }

}
