CREATE KEYSPACE IF NOT EXISTS bezbednost WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1} ;

DESCRIBE KEYSPACES;

USE bezbednost;

DROP TABLE IF EXISTS logs;

CREATE TABLE logs(
   house_id text,
   device_id text,
   exact_time timestamp,
   received_value float,
   PRIMARY KEY(house_id,device_id,exact_time)
)WITH CLUSTERING ORDER BY (device_id ASC,exact_time DESC);

INSERT INTO logs(house_id,device_id,exact_time,received_value)
   VALUES ('kuca1','uredjaj1','2023-05-25 22:55:54',28.2);
INSERT INTO logs(house_id,device_id,exact_time,received_value)
   VALUES ('kuca1','uredjaj1','2023-05-25 15:05:54',28.2);

