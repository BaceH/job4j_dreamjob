DROP TABLE IF EXISTS post;
DROP TABLE IF EXISTS city;
DROP TABLE IF EXISTS candidate;
DROP TABLE IF EXISTS userjob;

CREATE TABLE post (
   id SERIAL PRIMARY KEY,
   name TEXT
);

CREATE TABLE city (
  id SERIAL PRIMARY KEY,
  name TEXT
);

INSERT INTO city(id, name)
VALUES(1,'Tokyo'),
      (2,'Delhi'),
      (3,'Shanghai');

CREATE TABLE candidate (
   id SERIAL PRIMARY KEY,
   name TEXT,
   city_id INT
);

CREATE TABLE userjob (
   id SERIAL PRIMARY KEY,
   name TEXT,
   email TEXT,
   password TEXT
);