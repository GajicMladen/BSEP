CREATE KEYSPACE IF NOT EXISTS bezbednost WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1} ;

DESCRIBE KEYSPACES;

USE bezbednost;

DROP TABLE IF EXISTS logs;

CREATE TABLE logs(
   house_id int,
   device_id int,
   exact_time timestamp,
   received_value float,
   is_alarm boolean,
   PRIMARY KEY(house_id,exact_time,device_id)
)WITH CLUSTERING ORDER BY (exact_time DESC,device_id ASC);

INSERT INTO logs(house_id,device_id,exact_time,received_value,is_alarm)
   VALUES (1,1,'2023-05-25 22:55:54',28.2,false);
INSERT INTO logs(house_id,device_id,exact_time,received_value,is_alarm)
   VALUES (1,2,'2023-05-25 15:05:54',28.2,false);
INSERT INTO logs(house_id,device_id,exact_time,received_value,is_alarm)
   VALUES (1,2,'2023-05-25 15:05:58',28.2,true);
INSERT INTO logs(house_id,device_id,exact_time,received_value,is_alarm)
   VALUES (2,1,'2023-05-25 15:05:58',28.2,true);

