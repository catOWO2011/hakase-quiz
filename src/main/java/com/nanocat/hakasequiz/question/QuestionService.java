package com.nanocat.hakasequiz.question;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class QuestionService {
  private final QuestionDataAccessService questionDataAccessService;

  public QuestionService(QuestionDataAccessService questionDataAccessService) {
    this.questionDataAccessService = questionDataAccessService;
  }

  public List<Question> getAllQuestions() {
    return questionDataAccessService.selectAllQuestions();
  }

  public void addNewQuestion(Question question) {
    addNewQuestion(null, question);
  }

  public void addNewQuestion(UUID questionId, Question newQuestion) {
    UUID newQuestionId = Optional.ofNullable(questionId).orElse(UUID.randomUUID());
    questionDataAccessService.insertQuestion(newQuestionId, newQuestion);
  }

  public void deleteQuestion(UUID questionID) {
    questionDataAccessService.deleteQuestion(questionID);
  }
}
