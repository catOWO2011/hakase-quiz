package com.nanocat.hakasequiz.quiz;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class QuizController {
  private final QuizService quizService;

  public QuizController(QuizService quizService) {
    this.quizService = quizService;
  }

  @GetMapping("/quizzes")
  public List<Quiz> getAllQuizzes() {
    return quizService.getAllQuizzes();
  }

  @PostMapping("/quiz")
  public void addNewQuiz(@RequestBody @Valid Quiz newQuiz) {
    quizService.addNewQuiz(newQuiz);
  }

  @DeleteMapping("/quiz/{id}")
  public void deleteQuiz(@PathVariable UUID id) {
    quizService.deleteQuiz(id);
  }

  @DeleteMapping("/quiz/{id}/question/{questionId}")
  public void deleteQuizQuestion(@PathVariable UUID id, @PathVariable UUID questionId) {
    quizService.deleteQuizQuestion(id, questionId);
  }
}
