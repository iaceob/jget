

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
name varchar(30) not null,
cli varchar(50) not null,
path varchar(100) not null,
ctime timestamp default now(),
primary key (job)
);

create table j_cli(
id varchar(30) not null,
ip varchar(50) not null,
heartbeat timestamp,
ctime timestamp default now(),
primary key(id)
);



create view v_job_stat as
select js2.job, js1.stat, js2.ctime from j_job_stat as js1
right join (
select job, max(ctime) as ctime from j_job_stat group by job
) as js2 on js1.job=js2.job and js1.ctime=js2.ctime;









