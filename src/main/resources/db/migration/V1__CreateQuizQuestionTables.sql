CREATE TABLE IF NOT EXISTS quiz (
  id UUID PRIMARY KEY NOT NULL,
  quiz_name VARCHAR(100) NOT NULL UNIQUE
);
CREATE TABLE IF NOT EXISTS question (
  id UUID PRIMARY KEY NOT NULL,
  quiz_id UUID NOT NULL REFERENCES quiz(id),
  question TEXT,
  correct_answers TEXT [] [],
  question_type VARCHAR(20) NOT NULL CHECK (
    question_type = 'MULTIPLE'
    OR question_type = 'BOOLEAN'
    OR question_type = 'FILL_IN_THE_BLANK'
  )
);