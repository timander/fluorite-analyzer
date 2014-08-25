create table activity (
id bigint not null primary key auto_increment,
user varchar(50) not null,
logfile varchar(50) not null,
millisecond bigint not null,
event varchar(1000) not null,
session bigint null
)