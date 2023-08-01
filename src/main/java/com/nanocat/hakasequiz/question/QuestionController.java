package com.nanocat.hakasequiz.question;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.UUID;

@RestController
public class QuestionController {
  private final QuestionService questionService;

  public QuestionController(QuestionService questionService) {
    this.questionService = questionService;
  }

  @GetMapping("/questions")
  public List<Question> getAllQuestions() {
    return questionService.getAllQuestions();
  }

  @PostMapping("/question")
  public void addNewQuestion(@RequestBody Question question) {
    questionService.addNewQuestion(question);
  }


  /* It should be delete /quiz/{quizId}/question/{questionId}*/
  @DeleteMapping("/question/{questionId}")
  public void deleteQuestion(@PathVariable UUID questionId) {
    questionService.deleteQuestion(questionId);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public void handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
    String name = ex.getName();
    String type = ex.getRequiredType().getSimpleName();
    Object value = ex.getValue();
    String message = String.format("'%s' should be a valid '%s' and '%s' isn't",
            name, type, value);

    throw new IllegalStateException("Error Id");
  }
}
