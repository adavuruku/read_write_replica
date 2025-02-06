CREATE TABLE IF NOT EXISTS app_settings(
    id SERIAL PRIMARY KEY,
    group_id bigint not null,
    description text
);