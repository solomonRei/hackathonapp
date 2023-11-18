CREATE TABLE IF NOT EXISTS meetings
(
    id SERIAL PRIMARY KEY,
    meeting_name VARCHAR(255),
    official_id INT,
    meeting_link VARCHAR(255),
    meeting_date TIMESTAMP,
    meeting_location VARCHAR(255),
    description TEXT,
    FOREIGN KEY (official_id) REFERENCES officials (id)
);