package com.nanocat.hakasequiz.question;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Repository
public class QuestionDataAccessService {
  private static final String INSERT_QUESTION = "INSERT INTO question (id, quiz_id, question, correct_answers, question_type) VALUES (?, ?, ?, ?, ?)";
  private static final String SELECT_ALL_QUESTIONS = "SELECT id, quiz_id, question, correct_answers, question_type FROM question";
  private static final String DELETE_QUESTION_FROM_QUIZ = "DELETE FROM question WHERE id = ? AND quiz_id = ?";

  private final JdbcTemplate jdbcTemplate;

  public QuestionDataAccessService(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public int insertQuestion(UUID questionId, Question newQuestion) {

    return jdbcTemplate.update(INSERT_QUESTION,
            questionId,
            newQuestion.getQuizId(),
            newQuestion.getQuestion(),
            newQuestion.getCorrectAnswers().toArray(new String[0]),
            newQuestion.getQuestionType().name().toUpperCase()
    );
  }

  public List<Question> selectAllQuestions() {
    return jdbcTemplate.query(SELECT_ALL_QUESTIONS, (resultSet, rowNum) -> {
      UUID questionId = UUID.fromString(resultSet.getString("id"));
      UUID quizId = UUID.fromString(resultSet.getString("quiz_id"));
      String question = resultSet.getString("question");
      List<String> correctAnswers = Arrays.asList((String[])resultSet.getArray("correct_answers").getArray());
      Question.QuestionType questionType = Question.QuestionType.valueOf(resultSet.getString("question_type"));

      return new Question(
              questionId,
              quizId,
              question,
              questionType,
              correctAnswers
      );
    });
  }

  public int deleteQuestion(UUID questionId) {
    String sql = "DELETE FROM question WHERE id = ?";
    return jdbcTemplate.update(sql, questionId);
  }
}
