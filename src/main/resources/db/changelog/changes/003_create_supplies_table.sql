CREATE TABLE IF NOT EXISTS supplies(
       id BIGSERIAL PRIMARY KEY,
       name VARCHAR(255) NOT NULL,
       product_id BIGINT NOT NULL REFERENCES products (id),
       amount INTEGER NOT NULL DEFAULT 0
);