create table model
(
    id    bigserial
        constraint model_pkey
            primary key,
    name varchar(255) unique
);

alter table model
    owner to drone;

create table state
(
    id    bigserial
        constraint state_pkey
            primary key,
    name varchar(255) unique
);

alter table state
    owner to drone;

create table drone
(
    id               bigserial
        constraint drone_pkey
            primary key,
    name             varchar(255),
    battery_capacity integer,
    serial_number    varchar(100),
    weight           integer,
    model            bigint not null
        constraint fk_drone_to_model
            references model,
    state            bigint not null
        constraint fk_drone_to_state
            references state,
    medication       bigint
        constraint fk_drone_to_medication
            references state

);

alter table drone
    owner to drone;

create table image
(
    id    bigserial
        constraint image_pkey
            primary key,
    name bytea
);

alter table image
    owner to drone;

create table medication
(
    id     bigserial
        constraint medication_pkey
            primary key,
    code   varchar(255),
    name   varchar(255),
    weight integer,
    image  bigint
        constraint fk_medication_to_image
            references image
);

alter table medication
    owner to drone;
