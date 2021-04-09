CREATE TABLE post (
   id SERIAL PRIMARY KEY,
   name TEXT
);

CREATE TABLE candidate (
   id SERIAL PRIMARY KEY,
   name TEXT
);

CREATE TABLE userjob (
   id SERIAL PRIMARY KEY,
   name TEXT,
   email TEXT,
   password Text
);