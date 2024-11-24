CREATE TABLE products
(
    id      BIGSERIAL PRIMARY KEY,
    name    VARCHAR(255)     NOT NULL,
    info    VARCHAR(4096),
    price   DOUBLE PRECISION NOT NULL DEFAULT 0,
    available BOOLEAN          NOT NULL DEFAULT FALSE
);
