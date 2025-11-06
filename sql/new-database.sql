CREATE DATABASE mySambleDb;

CREATE SCHEMA mysampleschema;

CREATE TABLE my_new_table (
  id INTEGER PRIMARY KEY,
  email VARCHAR(100),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  salary FLOAT CHECK (salary > 4),
  second_salary DOUBLE PRECISION,
  third_salary DECIMAL,
  fourth_salary NUMERIC(10, 2)
);

CREATE TABLE another_table (id INTEGER PRIMARY KEY, name VARCHAR(255));

ALTER TABLE another_table
ADD COLUMN new_table_id INTEGER;

ALTER TABLE another_table
ALTER COLUMN new_table_id
SET NOT NULL;

ALTER TABLE another_table
DROP CONSTRAINT fk_new_table_id;

ALTER TABLE another_table
ADD CONSTRAINT fk_new_table_id FOREIGN key (new_table_id) REFERENCES my_new_table (id);