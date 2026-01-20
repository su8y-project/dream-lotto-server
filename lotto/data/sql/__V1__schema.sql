CREATE TABLE lotto_ticket
(
    id                   BIGINT PRIMARY KEY,
    user_id              BIGINT       NOT NULL,
    lotto_numbers        VARCHAR(255) NOT NULL,
    generation_algorithm VARCHAR(255),
    created_at           TIMESTAMP    NOT NULL
);
