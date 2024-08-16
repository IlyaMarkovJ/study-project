CREATE TABLE users
(
    id VARCHAR(100) primary key,
    login VARCHAR(100) not null unique,
    names VARCHAR(100) not null,
    last_names VARCHAR(100) not null,
    creation_date TIMESTAMP NOT NULL
);

insert into users(id,
                  login,
                  names,
                  last_names,
                  creation_date)
values ('123', 'login', 'name1', 'surname1', '2024-08-15');

select * from users where id = '123';

