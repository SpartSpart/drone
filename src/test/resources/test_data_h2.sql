insert into image (id, image)
values (1, E '\\x89504E470D0A1A0A000'),
       (2, E '\\x89504E470D0A1A0A000'),
       (3, E '\\x89504E470D0A1A0A000');

insert into model (id, name, weight_limit)
values (1, 'Lightweight', 200),
       (2, 'Middleweight', 300),
       (3, 'Cruiserweight', 400),
       (4, 'Heavyweight', 500);

insert into state (id, name)
values (1, 'IDLE'),
       (2, 'LOADING'),
       (3, 'LOADED'),
       (4, 'DELIVERING'),
       (5, 'DELIVERED'),
       (6, 'RETURNING');

insert into medication (id, name, code, weight, image)
values (1, 'Aspirin_01-1', 'AS_01', 10, 1),
       (2, 'Suprastin_02-2', 'SUPR_02', 20, 2),
       (3, 'Sebozol_03-3', 'SEB_03', 30, 3),
       (4, 'Finalgon_04-4', 'FIN_04', 40, 1),
       (5, 'Bint_05-5', 'BINT_05', 50, 2);

insert into drone (id, name, model, serial_number, battery_capacity, state)
values (1, 'DRONE_1', 1, 'SN345728346582365', 20, 1),
       (2, 'DRONE_2', 2, 'SN23488379325734507', 20, 2),
       (3, 'DRONE_3', 3, 'SN34985734259037500', 30, 2),
       (4, 'DRONE_4', 4, 'SN23958732498534778', 75, 4),
       (5, 'DRONE_5', 1, 'SN39408532753957203', 100, 5);

insert into drone_medication (drone_id, medication_id)
values (3, 1),
       (3, 2),
       (3, 3),
       (4, 4),
       (5, 1),
       (4, 2);
