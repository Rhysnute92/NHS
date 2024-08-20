package uk.ac.cf.spring.nhs.Question.Model;

import java.io.Serializable;

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
@Table(name = "Question")
public class Question implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QuestionID")
    private Long questionID;

    @Column(name = "QuestionText", columnDefinition = "TEXT")
    private String questionText;

    @Column(name = "QuestionCategory", length = 255)
    private String questionCategory;

    @Column(name = "QuestionResponseType", length = 255)
    private String questionResponseType;

    @ManyToOne
    @JoinColumn(name = "QuestionnaireID", nullable = false)
    private Questionnaire questionnaire;

    // Getters and Setters

    /**
     * Retrieves the unique identifier of the question.
     *
     * @return the unique identifier of the question
     */
    public Long getQuestionID() {
        return questionID;
    }

    /**
     * Sets the unique identifier of the question.
     *
     * @param questionID the unique identifier of the question to be set
     */
    public void setQuestionID(Long questionID) {
        this.questionID = questionID;
    }

    /**
     * Retrieves the question text.
     *
     * @return the question text
     */
    public String getQuestionText() {
        return questionText;
    }

    /**
     * Sets the question text.
     *
     * @param questionText the new question text to be set
     */
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    /**
     * Retrieves the question category.
     *
     * @return the question category
     */
    public String getQuestionCategory() {
        return questionCategory;
    }

    /**
     * Sets the question category.
     *
     * @param questionCategory the category of the question to be set
     * @throws IllegalArgumentException if the question category is null or exceeds
     *                                  255 characters in length
     */
    public void setQuestionCategory(String questionCategory) {
        if (questionCategory != null && questionCategory.length() > 255) {
            throw new IllegalArgumentException("QuestionCategory length must be less than or equal to 255 characters");
        }
        this.questionCategory = questionCategory;
    }

    public String getQuestionResponseType() {
        return questionResponseType;
    }

    /**
     * Sets the question response type.
     *
     * @param questionResponseType the question response type to be set
     */
    public void setQuestionResponseType(String questionResponseType) {
        if (questionResponseType != null && questionResponseType.length() > 255) {
            throw new IllegalArgumentException(
                    "QuestionResponseType length must be less than or equal to 255 characters");
        }
        this.questionResponseType = questionResponseType;
    }

    /**
     * Retrieves the questionnaire associated with this question.
     *
     * @return the questionnaire associated with this question
     */
    public Questionnaire getQuestionnaire() {
        return questionnaire;
    }

    /**
     * Sets the questionnaire associated with this question.
     *
     * @param questionnaire the questionnaire to be set
     */
    public void setQuestionnaire(Questionnaire questionnaire) {
        if (questionnaire == null) {
            throw new NullPointerException("Questionnaire cannot be null");
        }
        this.questionnaire = questionnaire;
    }
}
