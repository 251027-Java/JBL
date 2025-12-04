CREATE TABLE Expense (
    id varchar(255) NOT NULL,
    date timestamp(6),
    merchant varchar(255),
    price float(53),
    report_id varchar(255),
    PRIMARY KEY (id)
);

CREATE TABLE Report (
    id varchar(255) NOT NULL,
    status varchar(255),
    title varchar(255),
    PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS Expense
    ADD CONSTRAINT FKcxyk78fnaup0y4tgk8l7gd33h FOREIGN KEY (report_id) REFERENCES Report;

