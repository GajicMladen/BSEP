insert into users(email,name,lastName,password,pin,role)
        values ('mladengajic00@gmail.com','Mladen','Gajic','aa','11',1);
insert into users(email,name,lastName,password,pin,role)
        values ('djosa@gmail.com','Djordje','Jovanovic','bb','22',2);
insert into users(email,name,lastName,password,pin,role)
        values ('jovan@gmail.com','Jovan','Tomic','cc','33',2);
insert into users(email,name,lastName,password,pin,role)
        values ('dzoni@gmail.com','Nikola','Nikolic','ss','44',2);
insert into users(email,name,lastName,password,pin,role)
        values ('marko@gmail.com','Marko','Markovic','ff','55',2);


insert into realestate(address, area)
        values('Janka cmelika 1','50');
insert into realestate(address, area)
        values('Veselina Maslese 2','102');
insert into realestate(address, area)
        values('Dr Ivana Ribara 13','89');
insert into realestate(address, area)
        values('Bulevar Cara Lazara 7a','10');


insert into user_realestates(user_id,realestate_id) values (1,1);
insert into user_realestates(user_id,realestate_id) values (2,2);
insert into user_realestates(user_id,realestate_id) values (3,3);
insert into user_realestates(user_id,realestate_id) values (4,4);
insert into user_realestates(user_id,realestate_id) values (5,1);