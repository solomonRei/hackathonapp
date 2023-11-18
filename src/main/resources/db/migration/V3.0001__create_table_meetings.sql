CREATE TABLE IF NOT EXISTS meetings
(
    id SERIAL PRIMARY KEY,
    official_id INT,
    meeting_link VARCHAR(255),
    meeting_date TIMESTAMP,
    FOREIGN KEY (official_id) REFERENCES officials (id)
);