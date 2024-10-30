SELECT * FROM super_market_billing.invoice_items;

show databases;

create database super_market_billing_system;
use super_market_billing_system;
drop table user;
create table user(id int auto_increment primary key, name varchar(100) not null, age int check(age >= 0),
		password varchar(100) not null, 
        email varchar(100) not null unique, phone_number varchar(10) , address varchar(200),
        Modified_Time timestamp default current_timestamp on update current_timestamp);

create table customer(user_id int primary key, points int default 0, current_credit DECIMAL(10, 2) default 0.0,  FOREIGN KEY (user_id) REFERENCES user(id) on delete cascade ); 
create table admin(user_id int primary key, admin_privileges varchar(10), FOREIGN KEY (user_id) REFERENCES user(id) on delete cascade   );
insert into user (name, age, password, email, phone_number, address) values("Customer1", 22, "$2a$10$d5rf/itjIlbihEdbodH4.emTWfrk8uPYWPGAENmmEvFt698z6gaby", "srigirit369@gmail.com", 
	"9344953235", "12/24, Street 1, Address details goes here");
insert into admin(user_id) value(1);
update admin set admin_privileges = "ADMIN" where user_id = 1;
truncate table admin;
ALTER TABLE admin MODIFY COLUMN admin_privileges ENUM("ADMIN", "CASHIER", "INVENTORY_MANAGER");
select * from user;
select * from customer;
select * from admin;
insert into customer (user_id) values(1);
insert into 

SELECT 
    u.id AS user_id,
    u.name,
    u.age,
    u.phone_number,
    CASE 
        WHEN c.user_id IS NOT NULL THEN 'CUSTOMER'
        WHEN a.user_id IS NOT NULL THEN 'ADMIN'
        ELSE 'unknown'
    END AS role,
    COALESCE(c.points, 0) AS points
FROM 
    user u
LEFT JOIN 
    customer c ON u.id = c.user_id
LEFT JOIN 
    admin a ON u.id = a.user_id
WHERE 
    u.phone_number = "9344953235"; -- Replace :username with the input username
SELECT     u.id,      u.name,    u.age,u.phone_number, u.password , 
CASE    WHEN c.user_id IS NOT NULL THEN 'CUSTOMER'  WHEN a.user_id IS NOT NULL THEN 'ADMIN' 
       ELSE 'unknown' END AS role,   COALESCE(c.points, 0) AS points, 
       COALESCE(c.current_credit, 0) AS current_credit FROM     user u LEFT JOIN     customer c ON u.id = c.user_id LEFT JOIN  admin a ON u.id = a.user_id WHERE     u.phone_number = ?;
