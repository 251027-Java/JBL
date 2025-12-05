CREATE SEQUENCE account_SEQ
    START WITH 1
    INCREMENT BY 50;

CREATE TABLE account (
    id bigint NOT NULL,
    password varchar(255) NOT NULL,
    user_role varchar(255) NOT NULL,
    username varchar(255) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

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
    ADD CONSTRAINT fk_report_id FOREIGN KEY (report_id) REFERENCES report ON DELETE SET NULL;

