CREATE TABLE IF NOT EXISTS categories
(
    category_id   SERIAL PRIMARY KEY,
    category_name VARCHAR(255),
    description   TEXT
);