CREATE TABLE IF NOT EXISTS questions
(
    question_id   SERIAL PRIMARY KEY,
    question_title VARCHAR(255),
    question_text TEXT,
    vote_count INT DEFAULT 0,
    meeting_id INT,
    user_id       SERIAL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users_table (id),
    FOREIGN KEY (meeting_id) REFERENCES meetings (id)
);