package com.nanocat.hakasequiz.quiz;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class QuizService {
  private final QuizDataAccessService quizDataAccessService;

  public QuizService(QuizDataAccessService quizDataAccessService) {
    this.quizDataAccessService = quizDataAccessService;
  }

  public List<Quiz> getAllQuizzes() {
    return quizDataAccessService.selectAllQuizzes();
  }

  public void addNewQuiz(Quiz newQuiz) {
    addNewQuiz(null, newQuiz);
  }

  public void addNewQuiz(UUID quizId, Quiz newQuiz) {
    UUID newQuizId = Optional.ofNullable(quizId).orElse(UUID.randomUUID());

    quizDataAccessService.insertQuiz(newQuizId, newQuiz);
  }

  public void deleteQuiz(UUID id) {
    quizDataAccessService.deleteQuiz(id);
  }

  public void deleteQuizQuestion(UUID id, UUID questionId) {
    quizDataAccessService.deleteQuestionIfQuizExist(id, questionId);
  }
}
