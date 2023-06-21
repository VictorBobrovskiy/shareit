DROP TABLE IF EXISTS users, item_request, item, comment, booking cascade;

CREATE TABLE IF NOT EXISTS users
(
    id    BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name  VARCHAR(63)                             NOT NULL,
    email VARCHAR(255) UNIQUE                     NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT UQ_users_email UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS item_request
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    description VARCHAR(4000)                           NOT NULL,
    user_id     BIGINT                                  NOT NULL,
    created     TIMESTAMP                               NOT NULL,
    CONSTRAINT pk_item_request PRIMARY KEY (id),
    CONSTRAINT fk_item_request_users FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS item
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    user_id     BIGINT                                  NOT NULL,
    name        VARCHAR(255)                            NOT NULL,
    description VARCHAR(4000)                           NOT NULL,
    request_id  BIGINT,
    available   BOOLEAN                                 NOT NULL,
    CONSTRAINT pk_item PRIMARY KEY (id),
    CONSTRAINT fk_item_users FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_item_item_request FOREIGN KEY (request_id) REFERENCES item_request (id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS comment
(
    id      BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    text    VARCHAR(4000)                           NOT NULL,
    user_id BIGINT                                  NOT NULL,
    item_id BIGINT                                  NOT NULL,
    created TIMESTAMP                               NOT NULL,
    CONSTRAINT pk_comment PRIMARY KEY (id),
    CONSTRAINT fk_comment_users FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_comment_item FOREIGN KEY (item_id) REFERENCES item (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS booking
(
    id      BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    start   TIMESTAMP                               NOT NULL,
    finish  TIMESTAMP                               NOT NULL,
    item_id BIGINT                                  NOT NULL,
    user_id BIGINT                                  NOT NULL,
    status  VARCHAR(31)                             NOT NULL,
    CONSTRAINT pk_booking PRIMARY KEY (id),
    CONSTRAINT fk_booking_item FOREIGN KEY (item_id) REFERENCES item (id) ON DELETE CASCADE,
    CONSTRAINT fk_booking_users FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
