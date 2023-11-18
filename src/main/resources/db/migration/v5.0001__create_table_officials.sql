CREATE TABLE IF NOT EXISTS officials
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    position VARCHAR(255),
    --contact_info VARCHAR(255),
    bio TEXT,
    user_id SERIAL,
    FOREIGN KEY (user_id) REFERENCES users_table (id)
);