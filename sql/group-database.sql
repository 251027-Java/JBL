CREATE DATABASE groupdb;

CREATE TABLE employee (
  id INTEGER PRIMARY KEY,
  first_name VARCHAR(30),
  last_name VARCHAR(30),
  assignation_id INTEGER
);

CREATE TABLE assignation (
  id INTEGER PRIMARY KEY,
  name VARCHAR(30),
  salary INTEGER
);

CREATE TABLE department (id INTEGER PRIMARY KEY, name VARCHAR(30));

CREATE TABLE workplace (employee_id INTEGER, department_id INTEGER);

ALTER TABLE employee
ADD CONSTRAINT fk_assignation_id FOREIGN KEY (assignation_id) REFERENCES assignation (id);

ALTER TABLE employee
ALTER COLUMN assignation_id
SET NOT NULL;

ALTER TABLE workplace
ALTER COLUMN employee_id
SET NOT NULL,
ALTER COLUMN department_id
SET NOT NULL;

ALTER TABLE workplace
ADD CONSTRAINT fk_employee_id FOREIGN KEY (employee_id) REFERENCES employee (id);

ALTER TABLE workplace
ADD CONSTRAINT fk_department_id FOREIGN KEY (department_id) REFERENCES department (id);

INSERT INTO
  assignation (id, name, salary)
VALUES
  (1, 'Human Resources Manager', 60000),
  (2, 'Entry Level Software Engineer', 70000),
  (3, 'Junior Software Engineer', 90000),
  (4, 'Senior Software Engineer', 120000);

INSERT INTO
  department (id, name)
VALUES
  (1, 'Human Resources'),
  (2, 'Finance and Accounting'),
  (3, 'Operations and Logistics'),
  (4, 'Legal and Compliance'),
  (5, 'Software Engineering');

INSERT INTO
  employee (id, first_name, last_name, assignation_id)
VALUES
  (1, 'David', 'Johnson', 2),
  (2, 'John', 'Smith', 3),
  (3, 'Emily', 'Miller', 1),
  (4, 'James', 'Taylor', 4);

INSERT INTO
  workplace (employee_id, department_id)
VALUES
  (1, 5),
  (2, 5),
  (3, 1),
  (4, 5),
  (3, 2);

ALTER TABLE workplace
RENAME TO employee_department_association;