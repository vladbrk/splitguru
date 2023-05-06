#!/bin/bash


sudo -u postgres psql -d sharebill << END
create database sharebill;
create user biryukov with encrypted password '123456';
grant all privileges on database sharebill to biryukov;

CREATE SCHEMA sharebill;
GRANT ALL ON SCHEMA sharebill TO biryukov;
GRANT ALL ON ALL TABLES IN SCHEMA sharebill TO biryukov;
END










CREATE TABLE sharebill.room (
    id uuid primary key,
    room varchar
);
CREATE TABLE sharebill.product (
    id uuid primary key,
    name varchar,
    price integer,
    room_id uuid,
    foreign key (room_id) references sharebill.room(id)
);
CREATE TABLE sharebill.person (
    id uuid primary key,
    name varchar,
    room_id uuid,
    foreign key (room_id) references sharebill.room(id)
);
CREATE TABLE sharebill.payer (
    product_id uuid,
    person_id uuid,
    primary key (product_id, person_id),
    foreign key (product_id) references sharebill.product(id),
    foreign key (person_id) references sharebill.person(id)
);
CREATE TABLE sharebill.consumer (
    product_id uuid,
    person_id uuid,
    primary key (product_id, person_id),
    foreign key (product_id) references sharebill.product(id),
    foreign key (person_id) references sharebill.person(id)
);