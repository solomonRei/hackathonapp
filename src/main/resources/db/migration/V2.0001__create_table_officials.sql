CREATE TABLE IF NOT EXISTS officials
(
    id SERIAL PRIMARY KEY,
    position VARCHAR(255) DEFAULT NULL,
    work_place VARCHAR(255) DEFAULT NULL,
    bio TEXT,
    user_id SERIAL,
    FOREIGN KEY (user_id) REFERENCES users_table (id)
);