CREATE TABLE IF NOT EXISTS outbox_events (
    id UUID,
    aggregate_type VARCHAR(50) NOT NULL,
    aggregate_id BIGINT NOT NULL,
    type VARCHAR(50),
    payload TEXT NOT NULL,
    timestamp TIMESTAMP(6) NOT NULL,
    version INT,
    CONSTRAINT PK_OUT_BOX PRIMARY KEY (id)
);