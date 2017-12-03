
CREATE TABLE IF NOT EXISTS mail (
	id BIGINT ,
	subject varchar(255) not null,
	sender varchar(255) not null,
	recipient varchar(255) not null,
	message TEXT not null,
  sentDate VARCHAR (80),
  savedTime VARCHAR (80) not null
);




create sequence  hibernate_sequence start with 1 increment by 1;

