



create table j_account(
id varchar(30) not null,
name varchar(30) not null,
email varchar(50) not null,
passwd varchar(100) not null,
stat int default 1,
ctime timestamp default now(),
primary key(id)
);
create unique index uq_account_name on j_account(name);
comment on table j_account is '账户表';
comment on column j_account.id is '主键';
comment on column j_account.name is '帐户名';
comment on column j_account.email is '邮箱';
comment on column j_account.passwd is '密码';
comment on column j_account.stat is '状态';


create table j_job(
id varchar(30) not null,
name varchar(100) not null,
suffix varchar(10) not null,
sz int not null,
path varchar(30) not null,
url varchar(2000) not null,
type int not null,
cookie varchar(2000),
usr varchar(30) not null,
ctime timestamp default now(),
primary key(id)
);
comment on table j_job is '任务';
comment on column j_job.name is '文件名';
comment on column j_job.suffix is '后缀名';
comment on column j_job.sz is '文件尺寸';
comment on column j_job.path is '文件下载路径';
comment on column j_job.url is '下载地址';
comment on column j_job.type is '任务类型|HTTP|TORRENT|MAGNETIC';
comment on column j_job.cookie is 'cookie 内容';
comment on column j_job.usr is '任务创建人';


create table j_job_stat(
job varchar(30) not null,
stat varchar(20) not null,
ctime timestamp default now(),
primary key(job, ctime)
);
comment on table j_job_stat is '任务状态';
comment on column j_job_stat.job is '任务';
comment on column j_job_stat.stat is '状态';


create table j_job_progress(
job varchar(30) not null,
progress double precision not null,
mtime timestamp default now(),
primary key(job)
);
comment on table j_job_progress is '任务进度';
comment on column j_job_progress.job is '任务';
comment on column j_job_progress.progress is '进度';


create table j_job_cli(
job varchar(30) not null,
cli varchar(30) not null,
ctime timestamp default now(),
primary key (job)
);
comment on table j_job_cli is '任务执行机';
comment on column j_job_cli.job is '任务';
comment on column j_job_cli.cli is '客户机';


create table j_cli(
id varchar(30) not null,
name varchar(50) not null,
ip varchar(50) not null,
heartbeat timestamp,
usr varchar(30) not null,
ctime timestamp default now(),
primary key(id)
);
create unique index uq_cli_name_usr on j_cli(name, usr);
comment on table j_cli is '任务执行机';
comment on column j_cli.id is '客户机id';
comment on column j_cli.name is '客户机名';
comment on column j_cli.ip is '客户机ip';
comment on column j_cli.heartbeat is '心跳时间';
comment on column j_cli.usr is '客户机所属人';


create table j_cli_black(
cli varchar(30) not null,
target varchar(50) not null,
type varchar(10) not null,
ctime timestamp default now(),
primary key(cli, type)
);
comment on table j_cli_black is '客户机黑名单';
comment on column j_cli_black.cli is '客户机id';
comment on column j_cli_black.target is '客户机名称或者ip';
comment on column j_cli_black.type is '名称或者ip标识';


create view v_job_stat as
select js2.job, js1.stat, js2.ctime from j_job_stat as js1
right join (
select job, max(ctime) as ctime from j_job_stat group by job
) as js2 on js1.job=js2.job and js1.ctime=js2.ctime;


/*
drop table j_account;
drop table j_job;
drop table j_job_stat;
drop table j_job_progress;
drop table j_job_cli;
drop table j_cli;
drop table j_cli_account;
drop view v_job_stat;
*/







