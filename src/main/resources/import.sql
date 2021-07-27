/* DADOS PARA TESTE H2 e MySQL */
insert into telephone_charges (telephone_charge_id, origin, destination, per_minute_rate, created_at, updated_at) values (replace(uuid(), '-', ''),011, 016, 1.90, now(), now() );
insert into telephone_charges (telephone_charge_id, origin, destination, per_minute_rate, created_at, updated_at) values (replace(uuid(), '-', ''),016, 011, 2.90, now(), now() );
insert into telephone_charges (telephone_charge_id, origin, destination, per_minute_rate, created_at, updated_at) values (replace(uuid(), '-', ''),011, 017, 1.70, now(), now() );
insert into telephone_charges (telephone_charge_id, origin, destination, per_minute_rate, created_at, updated_at) values (replace(uuid(), '-', ''),017, 011, 2.7, now(), now() );
insert into telephone_charges (telephone_charge_id, origin, destination, per_minute_rate, created_at, updated_at) values (replace(uuid(), '-', ''),011, 018, 0.90, now(), now() );
insert into telephone_charges (telephone_charge_id, origin, destination, per_minute_rate, created_at, updated_at) values (replace(uuid(), '-', ''),018, 011, 1.90, now(), NOW() );


insert into plan (plan_id, description, minutes, surplus_percentage,active, created_at,updated_at) values (replace(uuid(), '-', ''), 'Tarifa fixa', 0, 0, true, now(), now());
insert into plan (plan_id, description, minutes, surplus_percentage,active, created_at,updated_at) values (replace(uuid(), '-', ''), 'FaleMais 30', 30, 10, true, now(), now());
insert into plan (plan_id, description, minutes, surplus_percentage,active, created_at,updated_at) values (replace(uuid(), '-', ''), 'FaleMais 60', 60, 10, true, now(), now());
insert into plan (plan_id, description, minutes, surplus_percentage,active, created_at,updated_at) values (replace(uuid(), '-', ''), 'FaleMais 120', 120, 10, true, now(), now());
