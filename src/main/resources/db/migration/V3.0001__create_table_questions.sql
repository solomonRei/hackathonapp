CREATE TABLE IF NOT EXISTS questions
(
    question_id   SERIAL PRIMARY KEY,
    category_id   INT,
    question_type VARCHAR(255),
    is_answered   BOOLEAN,
    is_active     BOOLEAN,
    user_id       SERIAL,
    FOREIGN KEY (user_id) REFERENCES users_table (id)
);