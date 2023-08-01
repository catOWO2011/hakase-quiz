package com.nanocat.hakasequiz.quiz;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class QuizDataAccessService {
  private static final String DELETE_QUESTIONS_FROM_QUIZ = "DELETE FROM question WHERE quiz_id = ?";
  private static final String DELETE_QUIZ = "DELETE FROM quiz WHERE id = ?";
  private static final String SELECT_ALL_QUIZZES = "SELECT id, quiz_name FROM quiz";
  private static final String INSERT_QUIZ = "INSERT INTO quiz (id, quiz_name) VALUES (?, ?)";
  private static final String DELETE_QUESTION_FROM_QUIZ = "DELETE FROM question\n" +
          "WHERE question.id = ? \n" +
          "  AND question.quiz_id = ? \n" +
          "  AND EXISTS (\n" +
          "    SELECT id\n" +
          "    FROM quiz\n" +
          "    WHERE id = ?\n" +
          "  )";
  private static final String COUNT_QUIZZES_BY_ID = "SELECT COUNT(*) FROM quiz WHERE id = ?";
  private static final String COUNT_QUESTION_BY_QUIZ_ID = "SELECT COUNT(*) FROM question WHERE question.quiz_id = ? AND id = ?";

  private final JdbcTemplate jdbcTemplate;

  public QuizDataAccessService(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<Quiz> selectAllQuizzes() {

    return jdbcTemplate.query(SELECT_ALL_QUIZZES, (resultSet, rowNum) -> {
      UUID quizId = UUID.fromString(resultSet.getString("id"));
      String quizName = resultSet.getString("quiz_name");

      return new Quiz(quizId, quizName);
    });
  }

  public void insertQuiz(UUID newQuizId, Quiz quiz) {
    try {
      jdbcTemplate.update(INSERT_QUIZ, newQuizId, quiz.getQuizName());
    } catch (DataAccessException exception) {
      throw new RuntimeException("Failed to insert new quiz.");
    }
  }

  public void deleteQuiz(UUID id) {

    try {
      jdbcTemplate.update(DELETE_QUESTIONS_FROM_QUIZ, id);
    } catch (DataAccessException deleteQuizQuestionsException) {
      throw new RuntimeException("Failed to delete quiz's questions.");
    }

    try {
      jdbcTemplate.update(DELETE_QUIZ, id);
    } catch (DataAccessException deleteQuizException) {
      throw new RuntimeException("Failed to delete quiz.");
    }
  }

  public void deleteQuestionIfQuizExist(UUID id, UUID questionId) {
    try {
      int count = jdbcTemplate.queryForObject(COUNT_QUIZZES_BY_ID, Integer.class, id);
      if (count == 0) {
        throw new RuntimeException("Quiz doesn't exist");
      }
    } catch (DataAccessException exception) {
      throw new RuntimeException("Failed to delete question from quiz");
    }

    try {
      int count = jdbcTemplate.queryForObject(COUNT_QUESTION_BY_QUIZ_ID, Integer.class, id, questionId);
      if (count == 0) {
        throw new RuntimeException("Question doesn't match Quiz id or doesn't exist");
      }
    } catch (DataAccessException exception) {
      throw new RuntimeException("Failed to check if question exists with quiz id");
    }

    try {
      jdbcTemplate.update(DELETE_QUESTION_FROM_QUIZ, questionId, id, id);
    } catch (DataAccessException exception) {
      throw  new RuntimeException("Failed to delete question from quiz");
    }
  }
}
