
    create sequence account_SEQ start with 1 increment by 50;

    create table account (
        id bigint not null,
        password varchar(255) not null,
        user_role varchar(255) not null,
        username varchar(255) not null unique,
        primary key (id)
    );

    create table expense (
        id varchar(255) not null,
        date timestamp(6),
        merchant varchar(255),
        price float(53),
        report_id varchar(255),
        primary key (id)
    );

    create table report (
        id varchar(255) not null,
        status varchar(255),
        title varchar(255),
        primary key (id)
    );

    alter table if exists expense 
       add constraint fk_report_id 
       foreign key (report_id) 
       references report 
       on delete set null;
