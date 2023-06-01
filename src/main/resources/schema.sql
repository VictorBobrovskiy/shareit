DROP TABLE IF EXISTS users, item, item_request, booking;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    name VARCHAR(63) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);
CREATE TABLE IF NOT EXISTS item_request (
    id BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    description VARCHAR(4000) NOT NULL,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE NOT NULL,
    created TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

CREATE TABLE IF NOT EXISTS item (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(4000) NOT NULL,
    request_id BIGINT REFERENCES item_request(id) ON DELETE SET NULL
);
-- CREATE TABLE IF NOT EXISTS status (
--     id   BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
--     name VARCHAR(31) NOT NULL
-- );

CREATE TABLE IF NOT EXISTS booking (
    id BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    start TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    finish TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    item_id BIGINT REFERENCES item(id) ON DELETE CASCADE NOT NULL,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE NOT NULL,
    status VARCHAR(31) NOT NULL
);