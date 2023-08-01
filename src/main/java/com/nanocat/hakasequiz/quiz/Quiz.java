package com.nanocat.hakasequiz.quiz;

import java.util.UUID;

public class Quiz {
  private final UUID quizId;
  private final String quizName;
  public Quiz(UUID quizId, String quizName) {
    this.quizId = quizId;
    this.quizName = quizName;
  }

  public UUID getQuizId() {
    return quizId;
  }

  public String getQuizName() {
    return quizName;
  }

  @Override
  public String toString() {
    return "Quiz{" +
            "quizId=" + quizId +
            ", quizName='" + quizName + '\'' +
            '}';
  }
}
