CREATE TABLE users
(
    id VARCHAR(100) primary key,
    login VARCHAR(100) not null unique,
    "name" VARCHAR(100) not null,
    last_name VARCHAR(100) not null,
    amount DOUBLE PRECISION,
    creation_date TIMESTAMP NOT NULL
);

insert into users(id,
                  login,
                  "name",
                  last_name,
                  amount,
                  creation_date)
values ('123', 'login', 'name1', 'surname1', 100.11 , '2024-08-15');

select * from users where id = '123';

