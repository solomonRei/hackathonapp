CREATE TABLE IF NOT EXISTS votes
(
    id SERIAL PRIMARY KEY,
    user_id INT,
    question_id INT,
    FOREIGN KEY (user_id) REFERENCES users_table (id),
    FOREIGN KEY (question_id) REFERENCES questions (question_id)
);