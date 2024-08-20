export class QuestionnaireDataFormatter {
  constructor() {
    // Initialize any properties if needed
  }

  parseAndFormatData(completedQuestionnaires) {
    console.log("Formatting the data:", completedQuestionnaires);

    // Example of how you might parse the data
    const formattedData = completedQuestionnaires.map((questionnaire) => {
      return {
        date: questionnaire.questionnaireCompletionDate,
        functionScore: this.calculateScore(questionnaire.responses, "Function"),
        appearanceScore: this.calculateScore(
          questionnaire.responses,
          "Appearance"
        ),
        symptomsScore: this.calculateScore(questionnaire.responses, "Symptoms"),
        emotionScore: this.calculateScore(questionnaire.responses, "Emotion"),
        qualityOfLifeScore: this.calculateScore(
          questionnaire.responses,
          "QualityOfLife"
        ),
      };
    });

    console.log("Formatted data:", formattedData);
    return formattedData;
  }

  calculateScore(responses, category) {
    // Example method to calculate a score for a given category
    return responses
      .filter((response) => response.question.questionCategory === category)
      .reduce(
        (total, response) => total + (response.userResponseScore || 0),
        0
      );
  }
}
