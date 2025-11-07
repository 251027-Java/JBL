CREATE DATABASE mysambledb;

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
ADD CONSTRAINT fk_new_table_id FOREIGN KEY (new_table_id) REFERENCES my_new_table (id);

INSERT INTO
  my_new_table (
    id,
    email,
    salary,
    second_salary,
    third_salary,
    fourth_salary
  )
VALUES
  (
    1,
    'asd@email.com',
    234,
    834.345,
    1000.434,
    1000.43
  ),
  (
    2,
    'asd@email.com',
    234,
    834.345,
    1000.434,
    1000.43
  ),
  (
    3,
    'asd@email.com',
    234,
    834.345,
    1000.434,
    1000.43
  ),
  (
    4,
    'asd@email.com',
    234,
    834.345,
    1000.434,
    1000.43
  ),
  (
    5,
    'asd@email.com',
    234,
    834.345,
    1000.434,
    1000.43
  )
ON CONFLICT DO NOTHING;

INSERT INTO
  another_table (id, name, new_table_id)
VALUES
  (1, 'james', 4),
  (2, 'emily', 1),
  (3, 'john', 3),
  (4, 'peter', 4),
  (5, 'sarah', 2),
  (6, 'mary', 5),
  (7, 'patricia', 2)
ON CONFLICT DO NOTHING;

INSERT INTO
  another_table (id, name, new_table_id)
VALUES
  (10, 'frank', 8)
ON CONFLICT DO NOTHING;

DELETE FROM my_new_table
WHERE
  my_new_table.id = 4;