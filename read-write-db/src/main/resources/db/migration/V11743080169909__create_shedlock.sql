CREATE TABLE IF NOT EXISTS shedlock (
    name VARCHAR(64) NOT NULL,
    lock_until TIMESTAMP(3) NULL,
    locked_at TIMESTAMP(3) NULL,
    locked_by VARCHAR(255) NOT NULL,
    aggregate_id BIGINT NOT NULL,
    CONSTRAINT PK_SHED_LOCK PRIMARY KEY (name)
);