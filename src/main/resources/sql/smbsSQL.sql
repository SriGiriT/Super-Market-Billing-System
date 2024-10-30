show databases;
create database super_market_billing;

use learning;

create table user(userName varchar(20), password varchar(20));
insert into user values("srigiri", "giripass"), ("sabari", "password");
select * from user;

use super_market_billing;
SELECT * FROM products WHERE LOWER(name) LIKE CONCAT('%', LOWER('O'), '%');

create table user(id bigint auto_increment primary key, user_name varchar(50) not null, 
		password varchar(100) not null, role ENUM('CUSTOMER', 'CASHIER', 'INVENTORY_MANAGER', 'ADMIN'), 
        email varchar(100) not null unique, phone_number varchar(10), points int default 0, current_credit double default 0, 
        Added_Time timestamp default current_timestamp, Modified_Time timestamp default current_timestamp on update current_timestamp);
alter table user modify column current_credit decimal(10,2) default 5000.0;
alter table products add column Added_Time timestamp default current_timestamp;
alter table products add column Modified_Time timestamp default current_timestamp on update current_timestamp;
alter table invoices add column Added_Time timestamp default current_timestamp, add column Modified_Time timestamp default current_timestamp on update current_timestamp;
alter table transactions
	add column cashier_id bigint;
alter table transactions 
	drop constraint fk_cashier_id;
alter table transactions 
	add constraint fk_cashier_id
    foreign key (cashier_id) REFERENCES user(id)
    on delete cascade;
select * from user;
select * from transactions;

select * from invoice_items;
select * from products;
update products set stock_left = 0 where id = 24;
alter table invoice_items drop foreign key invoice_items_ibfk_2;
alter table transactions drop foreign key transactions_ibfk_1;
delete from invoice_items where id = 30;
delete from invoices where id = 18;
delete from transactions where id = 9; 
delete from user where id=46;

ALTER TABLE invoice_items
ADD CONSTRAINT invoice_items_fk_product_id 
FOREIGN KEY (product_id) REFERENCES products(id) 
ON DELETE CASCADE;
CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,    
    name VARCHAR(100) NOT NULL,          
    description TEXT,                     
    stock_left INT NOT NULL,             
    usual_stock INT NOT NULL,           
    price DECIMAL(10, 2) NOT NULL, 
    seller_price DECIMAL(10, 2) NOT NULL, 
    total_sold_out INT DEFAULT 0         
);

CREATE TABLE transactions (
    id INT AUTO_INCREMENT PRIMARY KEY,       
    user_id Bigint NOT NULL,                   
    amount DECIMAL(10, 2) NOT NULL,        
    payment_method ENUM('Credit Card', 'Debit Card', 'Wallet', 'Net Banking', 'Cash') NOT NULL,  
    status ENUM('Pending', 'Completed', 'Failed') NOT NULL, 
    transaction_date DATETIME NOT NULL,     
    FOREIGN KEY (user_id) REFERENCES user(id) 
);
-- alter table transactions modify user_id bigint foreign key(user_id) references user(id) on delete cascade;
CREATE TABLE invoices (
    id INT AUTO_INCREMENT PRIMARY KEY,      
    user_id bigint NOT NULL,                  
    total_amount DECIMAL(10, 2) NOT NULL,   
    date DATETIME NOT NULL,                
    transaction_id INT,                    
    FOREIGN KEY (user_id) REFERENCES user(id), 
    FOREIGN KEY (transaction_id) REFERENCES transactions(id) 
);
CREATE TABLE invoice_items (
    id INT AUTO_INCREMENT PRIMARY KEY,      
    invoice_id INT NOT NULL,                 
    product_id INT NOT NULL,                
    quantity INT NOT NULL,                 
    price DECIMAL(10, 2) NOT NULL,         
    FOREIGN KEY (invoice_id) REFERENCES invoices(id), 
    FOREIGN KEY (product_id) REFERENCES products(id)  
);
drop table invoice_items;

drop table user;
select * from user;
update user set email = 'srigirit333@gmail.com' where id = 50;
delete from user where id in (53);
alter table user modify points  DECIMAL(10, 2) default 0;

INSERT INTO products (name, description, stock_left, usual_stock, price, seller_price, total_sold_out) 
VALUES
('Apple', 'Fresh red apples', 50, 100, 150, 100, 25),
('Banana', 'Organic ripe bananas', 60, 120, 80, 50, 30),
('Orange', 'Juicy oranges', 40, 90, 120, 80, 20),
('Milk', '1L full cream milk', 200, 250, 250, 180, 100),
('Bread', 'Whole grain bread loaf', 150, 200, 200, 150, 85),
('Eggs', 'Pack of 12 free-range eggs', 80, 100, 320, 250, 40),
('Butter', 'Salted butter 250g', 60, 80, 400, 320, 50),
('Cheese', 'Cheddar cheese 500g', 40, 70, 550, 400, 35),
('Rice', '5kg bag of white rice', 90, 150, 1000, 750, 70),
('Pasta', '500g pack of spaghetti', 120, 160, 220, 180, 55),
('Chicken', '1kg fresh chicken breast', 100, 140, 850, 700, 60),
('Beef', '500g lean ground beef', 75, 100, 650, 550, 45),
('Fish', '1kg frozen salmon fillets', 50, 70, 1200, 1000, 25),
('Tomato', 'Fresh ripe tomatoes', 100, 130, 250, 200, 50),
('Potato', '5kg bag of potatoes', 110, 150, 600, 450, 65),
('Onion', '1kg pack of onions', 90, 120, 180, 140, 45),
('Garlic', '500g fresh garlic', 70, 90, 300, 240, 35),
('Carrot', '1kg pack of carrots', 80, 110, 220, 180, 40),
('Broccoli', 'Fresh green broccoli', 60, 90, 350, 280, 30),
('Cucumber', 'Organic cucumbers', 50, 80, 150, 120, 20),
('Lettuce', 'Fresh iceberg lettuce', 55, 70, 180, 140, 25),
('Yogurt', '500g natural yogurt', 70, 100, 300, 250, 40),
('Ice Cream', '1L vanilla ice cream', 40, 60, 500, 400, 20),
('Coffee', '200g instant coffee', 100, 130, 800, 650, 50),
('Tea', '100g green tea bags', 110, 140, 450, 380, 60),
('Sugar', '1kg white sugar', 90, 120, 250, 180, 55),
('Salt', '1kg table salt', 80, 100, 100, 80, 30),
('Pepper', '100g black pepper', 70, 90, 350, 280, 40),
('Olive Oil', '500ml extra virgin olive oil', 60, 80, 750, 600, 35),
('Cereal', '500g pack of cornflakes', 100, 130, 380, 300, 50);
select * from products;
truncate table products;
select * from invoices;
select * from transactions;

SELECT p.id, p.name, SUM(ii.quantity) AS total_quantity_sold
	FROM products p JOIN invoice_items ii ON p.id = ii.product_id  GROUP BY p.id, p.name 
    ORDER BY total_quantity_sold DESC LIMIT 10;
    
    
SELECT 
	u.id As cashier_id,
    u.user_name AS cashier_name,
    COUNT(t.id) AS transaction_count
FROM 
    transactions t
JOIN 
    user u ON t.cashier_id = u.id
WHERE 
    t.cashier_id IS NOT NULL
GROUP BY 
    u.id, u.user_name
ORDER BY 
    transaction_count DESC;
    
update products set stock_left = 0 where id = 3;
update products set stock_left = 100 where id = 4;

SELECT 
    p.id,
    p.name,
    p.stock_left,
    p.price
FROM 
    products p
LEFT JOIN 
    invoice_items ii ON p.id = ii.product_id
WHERE 
    ii.product_id IS NULL;

SELECT 
    id,
    name,
    price
FROM 
    products
WHERE 
    stock_left = 0;

use super_market_billing;
select * from user;
select * from products;

update user u join 
	(
		select user_id, 
        sum(total_amount) as total_paid,
        sum(total_amount)/100 as total_points
        from invoices group by user_id
    ) as invoice_summary on u.id = invoice_summary.user_id
set u.points = invoice_summary.total_points;


WITH PaginatedProducts AS (
    SELECT *, ROW_NUMBER() OVER (ORDER BY id) AS row_num
    FROM Products
)
SELECT * 
FROM PaginatedProducts
WHERE row_num BETWEEN 16 AND 30;   

create table stock_replenishments(
	replenishment_id int not null auto_increment,
    product_id int not null, 
    replenished_quantity int not null, 
    replenished_by bigint not null, 
    replenishment_cost decimal(10, 2) not null, 
    replenishment_date timestamp null default current_timestamp, 
    primary key (replenishment_id),
    foreign key (product_id) references products(id) on delete cascade,
    foreign key (replenished_by) references user(id)
);
insert into stock_replenishments values(?, ?, ?, ?, ?);
drop table stock_replenishments;
CREATE TABLE `stock_replenishments` (
  `id` int NOT NULL AUTO_INCREMENT,
  `total_cost` decimal(10,2) NOT NULL,
  `order_date` datetime NOT NULL,
  `Added_Time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `Modified_Time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `inventory_manager_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_inventory_manager_id` (`inventory_manager_id`),
  CONSTRAINT `fk_inventory_manager_id` FOREIGN KEY (`inventory_manager_id`) REFERENCES `user` (`id`) ON DELETE SET NULL
);

CREATE TABLE `replenishment_items` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` int NOT NULL,
  `product_id` int NOT NULL,
  `quantity` int NOT NULL,
  `unit_cost` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_order_id` (`order_id`),
  KEY `fk_product_id` (`product_id`),
  CONSTRAINT `fk_order_id` FOREIGN KEY (`order_id`) REFERENCES `replenishment_orders` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_product_id` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

select sum(amount) as total_amount from transactions;

update user set current_credit = 100000 where role='INVENTORY_MANAGER';
select * from user where role = 'INVENTORY_MANAGER' or current_credit > 10000;

SELECT 
    ii.product_id,
    p.name as product_name,
    ii.quantity AS product_quantity,
    ii.price AS product_cost
FROM 
    transactions t
JOIN 
    invoices i ON t.id = i.transaction_id
JOIN 
    invoice_items ii ON i.id = ii.invoice_id
JOIN 
	products p ON ii.product_id = p.id
WHERE 
    t.id = 13;
    
    
select distinct(cashier_id) from transactions where cashier_id != 0;

update user set email = 'srigiri433@gmail.com' where id = 56; 
use super_market_billing;
select * from user;

