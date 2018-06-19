drop database if exists cmj18006_project;
create database cmj18006_project;
use cmj18006_project;

create table departments(id int primary key AUTO_INCREMENT,
name varchar(50),created_at timestamp default CURRENT_TIMESTAMP,
updated_at timestamp null,
status boolean default true);

insert into departments(name)
values('Administration'),('Payroll'),('Support'),
('Human Resource'),('billing');

create table employees(id int primary key AUTO_INCREMENT,
first_name varchar(50),last_name varchar(50),
email varchar(100) unique,contact_no varchar(20),
address varchar(100),
created_at timestamp default CURRENT_TIMESTAMP,
updated_at timestamp null,salary int,
status boolean default true);

insert into employees(first_name,last_name,email,contact_no,
status,salary)
values('Ram','kumar','ram@gmail.com','9796979',1,10000);
insert into employees(first_name,last_name,email,contact_no
,status,salary)
values('Rama','kumari','rama@gmail.com','9796979',1,
12000);
insert into employees(first_name,last_name,email,contact_no
,status,salary)
values('Ramila','kumari','ramila@gmail.com','9796979',1,15000)
;
insert into employees(first_name,last_name,email,contact_no
,status,salary)
values('Ramesh','kumar','ramesh@gmail.com','9796979',1,20000);

create table employee_departments(
id int primary key auto_increment,
department_id int,employee_id int ,
join_date date not null);

alter table employee_departments add foreign key
(department_id) references departments(id);
alter table employee_departments add foreign key
(employee_id) references employees(id);

insert into employee_departments(department_id,employee_id,
join_date) values(1,1,'2017-11-27'),
(2,1,'2017-12-27'),(3,3,'2017-11-27'),
(3,2,'2017-12-27');


create table customers(id int primary key AUTO_INCREMENT,
first_name varchar(50),last_name varchar(50),
email varchar(100) unique,contact_no varchar(20),
address varchar(100),
created_at timestamp default CURRENT_TIMESTAMP,
updated_at timestamp null,
status boolean default true);

create table complains(id int primary key AUTO_INCREMENT,
subject varchar(255),description text,
customer_id int,
complain_date timestamp default CURRENT_TIMESTAMP,
updated_at timestamp null,
status boolean default true);

alter table complains add foreign key(customer_id)
references customers(id);

insert into customers(first_name,last_name,email,contact_no,status)
values('Ram','kumar','ram@gmail.com','9796979',1);
insert into customers(first_name,last_name,email,contact_no,status)
values('Rama','kumari','rama@gmail.com','9796979',1);
insert into customers(first_name,last_name,email,contact_no,status)
values('Ramila','kumari','ramila@gmail.com','9796979',1);
insert into customers(first_name,last_name,email,contact_no,status)
values('Ramesh','kumar','ramesh@gmail.com','9796979',1);

insert into complains(subject,description,customer_id)
values('Problem','Problem 123',1);
insert into complains(subject,description,customer_id)
values('again problem','Problem 123',1);

insert into complains(subject,description,customer_id)
values('net again problem','Problem 123',3);



select concat(customers.first_name,' ',customers.last_name)as
customer_name
,if(cmp.total is null,0,cmp.total)as total from customers left join 
(select customer_id,count(1) as total from complains
group by customer_id)cmp on cmp.customer_id=customers.id;
