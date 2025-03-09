CREATE TABLE IF NOT EXISTS outbox_events (
    id UUID PRIMARY KEY,
    aggregate_type VARCHAR(50) NOT NULL,
    aggregate_id BIGINT NOT NULL,
    type VARCHAR(50),
    payload JSONB,
    timestamp TIMESTAMP(6) NOT NULL,
    version INT
);