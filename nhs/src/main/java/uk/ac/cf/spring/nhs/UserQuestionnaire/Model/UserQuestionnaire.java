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

    @Column(name = "user_id", nullable = false)
    private String userID;

    @Column(name = "questionnaire_start_date", nullable = false)
    private LocalDateTime questionnaireStartDate;

    @Column(name = "questionnaire_is_completed", nullable = false)
    private Boolean questionnaireIsCompleted = false;

    @Column(name = "questionnaire_completion_date")
    private LocalDateTime questionnaireCompletionDate;

    // Getters and Setters

    /**
     * Retrieves the unique identifier of the user questionnaire.
     *
     * @return the unique identifier of the user questionnaire
     */
    public Long getUserQuestionnaireId() {
        return userQuestionnaireId;
    }

    /**
     * Sets the unique identifier of the user questionnaire.
     *
     * @param userQuestionnaireId the unique identifier of the user questionnaire
     */
    public void setUserQuestionnaireId(Long userQuestionnaireId) {
        this.userQuestionnaireId = userQuestionnaireId;
    }

    /**
     * Retrieves the questionnaire associated with this user questionnaire.
     *
     * @return the questionnaire associated with this user questionnaire
     */
    public Questionnaire getQuestionnaire() {
        return questionnaire;
    }

    /**
     * Sets the questionnaire associated with this user questionnaire.
     *
     * @param questionnaire the questionnaire to be set
     */
    public void setQuestionnaire(Questionnaire questionnaire) {
        if (questionnaire == null) {
            throw new NullPointerException("Questionnaire cannot be null");
        }
        this.questionnaire = questionnaire;
    }

    /**
     * Retrieves the unique identifier of the user associated with the
     * questionnaire.
     *
     * @return the unique identifier of the user
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Sets the user ID associated with the questionnaire.
     *
     * @param userID the unique identifier of the user
     */
    public void setUserID(String userID) {
        if (userID == null) {
            throw new NullPointerException("User ID cannot be null");
        }
        this.userID = userID;
    }

    /**
     * Retrieves the start date of the questionnaire.
     *
     * @return the start date of the questionnaire
     */
    public LocalDateTime getQuestionnaireStartDate() {
        return questionnaireStartDate;
    }

    /**
     * Sets the start date of the questionnaire.
     *
     * @param questionnaireStartDate the start date of the questionnaire
     */
    public void setQuestionnaireStartDate(LocalDateTime questionnaireStartDate) {
        if (questionnaireStartDate == null) {
            throw new NullPointerException("Questionnaire start date cannot be null");
        }
        this.questionnaireStartDate = questionnaireStartDate;
    }

    /**
     * Retrieves the completion status of the questionnaire.
     *
     * @return true if the questionnaire is completed, false otherwise
     */
    public Boolean getQuestionnaireIsCompleted() {
        return questionnaireIsCompleted;
    }

    /**
     * Sets the completion status of the questionnaire.
     *
     * @param questionnaireIsCompleted whether the questionnaire is completed
     */
    public void setQuestionnaireIsCompleted(Boolean questionnaireIsCompleted) {
        this.questionnaireIsCompleted = questionnaireIsCompleted;
    }

    /**
     * Retrieves the completion date of the questionnaire.
     *
     * @return the completion date of the questionnaire
     */
    public LocalDateTime getQuestionnaireCompletionDate() {
        return questionnaireCompletionDate;
    }

    /**
     * Sets the completion date of the questionnaire.
     *
     * @param questionnaireCompletionDate the date the questionnaire was completed
     */
    public void setQuestionnaireCompletionDate(LocalDateTime questionnaireCompletionDate) {
        this.questionnaireCompletionDate = questionnaireCompletionDate;
    }
}
