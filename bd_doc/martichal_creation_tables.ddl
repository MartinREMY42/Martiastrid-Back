-- create user app with password app if does not exists
-- ___________________ 

DO
$do$
BEGIN
   IF NOT EXISTS (
      SELECT                   
      FROM   pg_catalog.pg_roles
      WHERE  rolname = 'app') THEN

      CREATE ROLE app LOGIN PASSWORD 'app';
   END IF;
END
$do$;

-- schema reset 
-- ___________________ 

CREATE SCHEMA pizza AUTHORIZATION app;


-- tables creation 
-- ___________________ 

create table pizza.users (
     username varchar(50) not null,
     password varchar(150) not null,
     enabled boolean not null,
     non_expired boolean not null,
     non_locked boolean not null,
     credentials_non_expired boolean not null,
     creation_date date not null,
     birth_date date,
     constraint IDusers primary key (username));

create table pizza.authorities (
     authority varchar(50) not null,
     constraint IDauthorities primary key (authority));

create table pizza.jt_user_authorities (
     id SERIAL not null,
     fk_user varchar(50) not null,
     fk_authority varchar(50) not null,
     constraint IDjt_user_authorities primary key (id));

create table pizza.ingredients (
     id SERIAL not null,
     generic_name varchar(32) not null,
     stock_quantity integer not null,
     price_for_composition integer not null,
     constraint IDingredients primary key (id));

create table pizza.orders (
     id SERIAL not null,
     fk_user varchar(50) not null,
     fk_payment integer,
     creation_date date not null,
     promo_name varchar(50),
     reduction integer not null,
     constraint IDorders primary key (id));

create table pizza.payment (
     id SERIAL not null,
     date date not null,
     ref_paypal varchar(50) not null,
     total integer not null,
     constraint IDpayment primary key (id));

create table pizza.order_line (
     id SERIAL not null,
     fk_order integer not null,
     fk_pizza integer,
     quantity integer not null,
     constraint IDorder_line primary key (id),
	 constraint uq_order_pizza UNIQUE(fk_order, fk_pizza));
	 
create table pizza.pizza_category(
	category  varchar(50) not null,
	constraint IDpizzaCategory primary key(category));

create table pizza.pizzas (
     id SERIAL not null,
     generic_name varchar(50) not null,
     price integer not null,
	 custom boolean not null,
	 delete_flag boolean not null DEFAULT false,
     constraint IDpizzas primary key (id));
	 
create table pizza.jt_pizza_category (
	 fk_pizza integer not null,
	 fk_category varchar(50) not null);
	 
create table pizza.jt_pizza_ingredient (
     id SERIAL not null,
     fk_ingredient integer not null,
     fk_pizza integer not null,
     constraint IDrecettes primary key (id));
										
create table pizza.jt_user_pizzas (
     id SERIAL not null,
     fk_user varchar(50) not null,
     fk_pizza integer not null,
     constraint IDjt_user_pizzas primary key (id));


-- Constraints Section
-- ___________________ 

alter table pizza.jt_user_authorities add constraint GRjt_user_authorities
     foreign key (fk_user)
     references pizza.users;

alter table pizza.jt_user_authorities add constraint GRjt_user_authorities_1
     foreign key (fk_authority)
     references pizza.authorities;

alter table pizza.orders add constraint GRorders
     foreign key (fk_user)
     references pizza.users;

alter table pizza.orders add constraint GRorders_1
     foreign key (fk_payment)
     references pizza.payment;

alter table pizza.order_line add constraint GRorder_line
     foreign key (fk_order)
     references pizza.orders;

alter table pizza.order_line add constraint GRorder_line_1
     foreign key (fk_pizza)
     references pizza.pizzas;

alter table pizza.jt_pizza_ingredient add constraint GRrecettes
     foreign key (fk_ingredient)
     references pizza.ingredients;

alter table pizza.jt_pizza_ingredient add constraint GRrecettes_1
     foreign key (fk_pizza)
     references pizza.pizzas;
	 
alter table pizza.jt_pizza_category add constraint UqJtPizzaCategory
	  UNIQUE(fk_pizza, fk_category);
	  
alter table pizza.orders add constraint UqordersUsernamePayment
	  UNIQUE(fk_user, fk_payment);
												   
alter table pizza.jt_user_pizzas add constraint GRjt_user_pizzas
     foreign key (fk_user)
     references pizza.users;

alter table pizza.jt_user_pizzas add constraint GRjt_user_pizzas_1
     foreign key (fk_pizza)
     references pizza.pizzas;

alter table pizza.jt_user_pizzas add constraint UqJtUserPizza
	  UNIQUE(fk_user, fk_pizza);
	 

-- authorisation on tables for app user 
-- ___________________ 
	 
GRANT ALL ON TABLE pizza.users TO app;
GRANT ALL ON TABLE pizza.authorities TO app;
GRANT ALL ON TABLE pizza.jt_user_authorities TO app;
GRANT ALL ON TABLE pizza.ingredients TO app;
GRANT ALL ON TABLE pizza.orders TO app;
GRANT ALL ON TABLE pizza.payment TO app;
GRANT ALL ON TABLE pizza.order_line TO app;
GRANT ALL ON TABLE pizza.pizzas TO app;
GRANT ALL ON TABLE pizza.jt_pizza_ingredient TO app;	
GRANT ALL ON TABLE pizza.pizza_category TO app;	
GRANT ALL ON TABLE pizza.jt_pizza_category TO app;
GRANT ALL ON TABLE pizza.jt_user_pizzas TO app;											  



-- authorisation on sequences for serials to user app
-- ___________________ 

GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA pizza TO app;