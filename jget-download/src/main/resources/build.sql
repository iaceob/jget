

create table j_job(
id varchar(30) not null,
name varchar(100) not null,
suffix varchar(10) not null,
sz int not null,
ctime timestamp default now(),
primary key(id)
);

create table j_job_stat(
job varchar(30) not null,
stat int not null,
ctime timestamp default now(),
primary key(job, ctime)
);

create table j_local(
job varchar(30) not null,
cli varchar(50) not null,
path varchar(100) not null,
ctime timestamp default now(),
primary key (job)
);








