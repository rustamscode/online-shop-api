CREATE TABLE IF NOT EXISTS products(
    id      BIGSERIAL PRIMARY KEY,
    name    VARCHAR(255)     NOT NULL,
    info    VARCHAR(4096),
    price   DOUBLE PRECISION NOT NULL DEFAULT 0,
    stock   INTEGER DEFAULT 0,
    available BOOLEAN GENERATED ALWAYS AS (stock > 0) STORED
);
