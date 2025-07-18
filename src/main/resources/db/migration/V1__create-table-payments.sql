CREATE TABLE IF NOT EXISTS payments (
    correlation_id UUID PRIMARY KEY,
    amount DECIMAL NOT NULL,
    type_payment VARCHAR(50) NOT NULL,
    requested_at TIMESTAMP NOT NULL
);
