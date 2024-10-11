CREATE TABLE IF NOT EXISTS users
(
    id VARCHAR(100) primary key,
    login VARCHAR(100) not null unique,
    name VARCHAR(100) not null,
    last_name VARCHAR(100) not null,
    amount DOUBLE PRECISION,
    creation_date TIMESTAMP NOT NULL
);

