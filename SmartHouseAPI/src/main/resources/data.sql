
-- USERS
INSERT INTO users(email, name, lastName, password, pin, active, unsuccessful_login_attempts)
	VALUES ('jtomic1@gmail.com', 'Jovan', 'Tomic', 'Test', '1234', true, 0);
INSERT INTO users(email, name, lastName, password, pin, active, unsuccessful_login_attempts)
	VALUES ('p3r5kul45@gmail.com', 'Marko', 'Markovic', '$2a$10$XeS1WZloSVVq2Z2dJd3L7ePADJy51sWu/oLqcy.Qcmppr6VcUtcr6', '1028', true, 0);
INSERT INTO users(email, name, lastName, password, pin, active, unsuccessful_login_attempts)
	VALUES ('jtomic@gmail.com', 'Nikola', 'Nikolic', '$2a$10$XeS1WZloSVVq2Z2dJd3L7ePADJy51sWu/oLqcy.Qcmppr6VcUtcr6', '1028', true, 0);
INSERT INTO users(email, name, lastName, password, pin, active, unsuccessful_login_attempts)
	VALUES ('a', 'Djimla', 'Djimlic', '$2a$10$XeS1WZloSVVq2Z2dJd3L7ePADJy51sWu/oLqcy.Qcmppr6VcUtcr6', '1028', true, 0);
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

insert into user_realestates(user_id,realestate_id) values (4,1);
insert into user_realestates(user_id,realestate_id) values (4,2);
insert into user_realestates(user_id,realestate_id) values (4,3);
insert into user_realestates(user_id,realestate_id) values (3,4);
insert into user_realestates(user_id,realestate_id) values (3,1);

INSERT INTO public.device(description, device_type, read_data, realestate_id,up_limit,down_limit)
	VALUES ( 'temperatura u dnevnoj sobi', 0, true, 1,30.2, 16.1);
INSERT INTO public.device(description, device_type, read_data, realestate_id,up_limit,down_limit)
	VALUES ( 'kamera u dvoristu', 1, true, 1, 1.0 , 0.0);
INSERT INTO public.device(description, device_type, read_data, realestate_id,up_limit,down_limit)
	VALUES ( 'ulazna vrata', 2, true, 1 , 1.0 , 0.0);
INSERT INTO public.device(description, device_type, read_data, realestate_id,up_limit,down_limit)
	VALUES ( 'temperatura u kuhinji', 0, true, 2 , 33.3 , 16.22);