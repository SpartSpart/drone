-- auto-generated definition
create table model
(
    id    bigserial
        constraint model_pkey
            primary key,
    model varchar(255)
);

alter table model
    owner to drone;

-- auto-generated definition
create table state
(
    id    bigserial
        constraint state_pkey
            primary key,
    state varchar(255)
);

alter table state
    owner to drone;

-- auto-generated definition
create table drone
(
    id               bigserial
        constraint drone_pkey
            primary key,
    battery_capacity integer,
    serial_number    varchar(255),
    weight           integer,
    model            bigint not null
        constraint fka3riqnq8ithq9ldnw0ioekwaj
            references model,
    state            bigint not null
        constraint fk1o6a9sab9p8rh51h5l0pvev0q
            references state
);

alter table drone
    owner to drone;



ALTER TABLE drone Add COLUMN test varchar;