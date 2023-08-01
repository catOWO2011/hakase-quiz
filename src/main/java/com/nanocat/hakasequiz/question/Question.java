package com.nanocat.hakasequiz.question;

import java.util.List;
import java.util.UUID;

public class Question {
  private final UUID quizId;
  private final UUID questionId;
  private final String question;
  private final QuestionType questionType;
  private final List<String> correctAnswers;

  enum QuestionType {
    MULTIPLE, BOOLEAN, FILL_IN_THE_BLANK
  };

  public Question(UUID questionId, UUID quizId, String question, QuestionType questionType, List<String> correctAnswers) {
    this.questionId = questionId;
    this.question = question;
    this.questionType = questionType;
    this.correctAnswers = correctAnswers;
    this.quizId = quizId;
  }

  public UUID getQuestionId() {
    return questionId;
  }

  public String getQuestion() {
    return question;
  }

  public QuestionType getQuestionType() {
    return questionType;
  }

  public List<String> getCorrectAnswers() {
    return correctAnswers;
  }

  public UUID getQuizId() {
    return quizId;
  }

  @Override
  public String toString() {
    return "Question{" +
            "questionId=" + questionId +
            ", question='" + question + '\'' +
            ", questionType=" + questionType +
            ", correctAnswers=" + correctAnswers +
            '}';
  }
}
