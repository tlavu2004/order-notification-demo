CREATE TABLE IF NOT EXISTS orders (
    id UUID PRIMARY KEY,
    customer_name VARCHAR(255) NOT NULL,
    total_amount NUMERIC(19, 2) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_orders_status ON orders(status);
CREATE INDEX IF NOT EXISTS idx_orders_created_at ON orders(created_at DESC);

INSERT INTO orders (id, customer_name, total_amount, status, created_at, updated_at) VALUES
(gen_random_uuid(), 'John Doe', 299.99, 'PENDING', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(gen_random_uuid(), 'Jane Smith', 150.50, 'CONFIRMED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(gen_random_uuid(), 'Bob Johnson', 450.00, 'SHIPPED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

