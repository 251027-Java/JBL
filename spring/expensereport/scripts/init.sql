CREATE TABLE expense (
    id varchar(255) NOT NULL,
    date timestamp(6),
    merchant varchar(255),
    price float(53),
    report_id varchar(255),
    PRIMARY KEY (id)
);

CREATE TABLE report (
    id varchar(255) NOT NULL,
    status varchar(255),
    title varchar(255),
    PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS expense
    ADD CONSTRAINT FKp2g4hmp5u0jyccj77jgxs9ct1 FOREIGN KEY (report_id) REFERENCES report ON DELETE SET NULL;

