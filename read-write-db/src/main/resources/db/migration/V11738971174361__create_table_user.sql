CREATE TABLE IF NOT EXISTS users(
    id SERIAL PRIMARY KEY,
    first_name varchar not null,
    country varchar
);