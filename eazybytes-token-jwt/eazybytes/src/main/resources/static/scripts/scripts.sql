CREATE DATABASE eazybytes;

CREATE TABLE IF NOT EXISTS eazybytes.users
(
    id       BIGINT      NOT NULL AUTO_INCREMENT,
    username VARCHAR(45) NOT NULL,
    password VARCHAR(45) NOT NULL,
    enabled  INT         NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS eazybytes.authorities
(
    id        BIGINT      NOT NULL AUTO_INCREMENT,
    username  VARCHAR(45) NOT NULL,
    authority VARCHAR(45) NOT NULL,
    PRIMARY KEY (id)
);

INSERT IGNORE INTO eazybytes.users (username, password, enabled)
VALUES ('happy', '12345', '1');
INSERT IGNORE INTO eazybytes.authorities (username, authority)
VALUES ('happy', 'write');

CREATE TABLE IF NOT EXISTS eazybytes.customers
(
    id    BIGINT       NOT NULL AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL,
    pwd   VARCHAR(255) NOT NULL,
    role  VARCHAR(45)  NOT NULL,
    PRIMARY KEY (id)
);

INSERT IGNORE INTO eazybytes.customers (email, pwd, role)
VALUES ('johndoe@example.com', '54321', 'admin');