
-- USERS
INSERT INTO users(email, name, lastName, password, pin, active, unsuccessful_login_attempts)
	VALUES ('jtomic1@gmail.com', 'Jovan', 'Tomic', 'Test', '1234', true, 0);
INSERT INTO users(email, name, lastName, password, pin, active, unsuccessful_login_attempts)
	VALUES ('p3r5kul45@gmail.com', 'Marko', 'Markovic', '$2a$10$JnSPxBt53ivBH7Youw9lVu7WU5M87GNNGG0puQkOihPyDaTV.S9wu', '1028', true, 0);
INSERT INTO users(email, name, lastName, password, pin, active, unsuccessful_login_attempts)
	VALUES ('jtomic@gmail.com', 'Nikola', 'Nikolic', '$2a$10$JnSPxBt53ivBH7Youw9lVu7WU5M87GNNGG0puQkOihPyDaTV.S9wu', '1028', true, 0);
INSERT INTO users(email, name, lastName, password, pin, active, unsuccessful_login_attempts)
	VALUES ('djimla@gmail.com', 'Djimla', 'Djimlic', '$2a$10$JnSPxBt53ivBH7Youw9lVu7WU5M87GNNGG0puQkOihPyDaTV.S9wu', '1028', true, 0);
-- ROLES
INSERT INTO role(name) VALUES('ROLE_USER');
INSERT INTO role(name) VALUES('ROLE_ADMIN');
INSERT INTO role(name) VALUES('ROLE_OWNER');

INSERT INTO user_role(user_id, role_id) VALUES (1, 1);
INSERT INTO user_role(user_id, role_id) VALUES (2, 1);
INSERT INTO user_role(user_id, role_id) VALUES (3, 2);
INSERT INTO user_role(user_id, role_id) VALUES (4, 3);
-- DJIMLINO
insert into realestate(address, area)
        values('Janka cmelika 1','50');
insert into realestate(address, area)
        values('Veselina Maslese 2','102');
insert into realestate(address, area)
        values('Dr Ivana Ribara 13','89');
insert into realestate(address, area)
        values('Bulevar Cara Lazara 7a','10');
--
insert into user_realestates(user_id,realestate_id) values (4,1);
insert into user_realestates(user_id,realestate_id) values (4,2);
insert into user_realestates(user_id,realestate_id) values (4,3);
insert into user_realestates(user_id,realestate_id) values (3,4);
insert into user_realestates(user_id,realestate_id) values (3,1);