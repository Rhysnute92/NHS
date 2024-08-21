package uk.ac.cf.spring.nhs.UserQuestionnaire.Model;

import java.time.LocalDateTime;
import java.time.LocalDate;

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
    @Column(name = "UserQuestionnaireID")
    private Long userQuestionnaireId;

    @ManyToOne
    @JoinColumn(name = "QuestionnaireID ", nullable = false)
    private Questionnaire questionnaire;

    @Column(name = "UserID", nullable = false)
    private Long userID;

    @Column(name = "QuestionnaireStartDate", nullable = true)
    private LocalDateTime questionnaireStartDate;

    @Column(name = "QuestionnaireCreatedDate", nullable = false)
    private LocalDateTime questionnaireCreatedDate;

    @Column(name = "QuestionnaireDueDate", nullable = false)
    private LocalDate questionnaireDueDate;

    @Column(name = "QuestionnaireInProgress", nullable = false)
    private Boolean questionnaireInProgress = false;

    @Column(name = "QuestionnaireIsCompleted", nullable = false)
    private Boolean questionnaireIsCompleted = false;

    @Column(name = "QuestionnaireCompletionDate")
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
    public Long getUserID() {
        return userID;
    }

    /**
     * Sets the user ID associated with the questionnaire.
     *
     * @param userID the unique identifier of the user
     */
    public void setUserID(Long userID) {
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
        this.questionnaireStartDate = questionnaireStartDate;
    }

    public Boolean getQuestionnaireInProgress() {
        return questionnaireInProgress;
    }

    public void setQuestionnaireInProgress(Boolean questionnaireInProgress) {
        this.questionnaireInProgress = questionnaireInProgress;
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

    /**
     * @return the questionnaireCreatedDate
     */
    public LocalDateTime getQuestionnaireCreatedDate() {
        return questionnaireCreatedDate;
    }

    /**
     * @param questionnaireCreatedDate the questionnaireCreatedDate to set
     */
    public void setQuestionnaireCreatedDate(LocalDateTime questionnaireCreatedDate) {
        this.questionnaireCreatedDate = questionnaireCreatedDate;
    }

    /**
     * @return the questionnaireDueDate
     */
    public LocalDate getQuestionnaireDueDate() {
        return questionnaireDueDate;
    }

    /**
     * @param questionnaireDueDate the questionnaireDueDate to set
     */
    public void setQuestionnaireDueDate(LocalDate questionnaireDueDate) {
        this.questionnaireDueDate = questionnaireDueDate;
    }

}
