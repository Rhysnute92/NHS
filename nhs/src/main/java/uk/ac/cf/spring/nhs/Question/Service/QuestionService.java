package uk.ac.cf.spring.nhs.Question.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import uk.ac.cf.spring.nhs.Question.Model.Question;
import uk.ac.cf.spring.nhs.Question.Repository.JpaQuestionRepo;

public class QuestionService {
    @Autowired
    private JpaQuestionRepo questionRepository;

    /**
     * Retrieves a list of questions associated with a specific questionnaire.
     *
     * @param questionnaireId the unique identifier of the questionnaire
     * @return a list of questions linked to the specified questionnaire
     */
    public List<Question> getQuestionsByQuestionnaireId(Long questionnaireId) {
        return questionRepository.findByQuestionnaireId(questionnaireId);
    }

    /**
     * Retrieves a list of questions associated with a specific category.
     *
     * @param category the category of questions to be retrieved
     * @return a list of questions linked to the specified category
     */
    public List<Question> getQuestionsByCategory(String category) {
        return questionRepository.findByQuestionCategory(category);
    }

    /**
     * Retrieves a list of questions associated with a specific response type.
     *
     * @param responseType the type of response to filter questions by
     * @return a list of questions that match the specified response type
     */
    public List<Question> getQuestionsByResponseType(String responseType) {
        return questionRepository.findByQuestionResponseType(responseType);
    }


    /**
     * Saves a question to the repository.
     *
     * @param question the question to be saved
     * @return the saved question
     */
    public Question saveQuestion(Question question) {
        if (question.getQuestionnaire() == null) {
            throw new NullPointerException("Questionnaire cannot be null");
        }
        return questionRepository.save(question);
    }

    /**
     * Deletes a question from the repository based on the provided question ID.
     *
     * @param questionId the ID of the question to be deleted
     */
    public void deleteQuestion(Long questionId) {
        questionRepository.deleteById(questionId);
    }

}
