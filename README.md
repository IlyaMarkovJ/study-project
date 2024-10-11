# study-project

## Usage

*Get all users*

```shell
curl --location 'localhost:8080/api/v1/users'
```

*Get user*

```shell
curl --location 'localhost:8080/api/v1/users/{id}'
```

*User counter*

```shell
curl --location 'localhost:8080/api/v1/users/counter'
```

*Create user*

```shell
curl --location 'localhost:8080/api/v1/users' \
--data '{
    "login": "test",
    "name": "testName",
    "lastName": "testLastName",
    "amount": 20.0
}'
```
*Update balance*

```shell
curl --location --request POST 'localhost:8080/api/v1/users/{id}/up-balance/20.0'
```

*Update user*

```shell
curl --location --request PUT 'localhost:8080/api/v1/users/{id} \
--header 'Content-Type: application/json' \
--data '{
    "name": "test2",
    "lastName": "lastName2"
}'
```

*Delete user*

```shell
curl --location --request DELETE 'localhost:8080/api/v1/users/{id}'
```

## Start database

### Download Docker

### Start Postgres

```shell
docker run --name sql-postgres-study -p 5432:5432 -e POSTGRES_PASSWORD=postgres -d postgres:15.2

docker start sql-postgres-study
docker stop sql-postgres-study
docker rm sql-postgres-study
```

### Connect to Postgres

```shell
docker exec -it sql-postgres-study bash
psql -U postgres
```

```sql
CREATE DATABASE postgres_study;
\c postgres_study
CREATE ROLE postgres_study WITH LOGIN PASSWORD 'password';
CREATE SCHEMA postgres_study authorization postgres_study;

-- Do in another terminal or in another connection
-- psql -U postgres_study postgres_study
create table example(id integer);
\dt

select *
from example;
```

### GUI for Database

### Useful commands

```sql
ALTER TABLE postgres_study.example ADD "name" varchar NULL;

ALTER TABLE postgres_study.example ADD surname varchar NULL;

insert into example(id, name, surname)
values (2, 'Bob', 'Spanch');

select * from example;
```