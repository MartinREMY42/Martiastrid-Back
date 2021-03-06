-- roles
-- ___________________ 

INSERT INTO pizza.authorities(
    authority)
    VALUES ('ROLE_USER');
INSERT INTO pizza.authorities(
    authority)
    VALUES ('ROLE_ADMIN');


-- users
-- ___________________

INSERT INTO pizza.users(
    username, password, enabled, non_expired, non_locked, credentials_non_expired, creation_date, birth_date)
    VALUES ('jean', '$2a$10$p0KmgWXrtAGRa5h3xasbj.hZ6yZjDLgvUeJEzOqW/U1XpGTPnRdbG', true, true, true, true, '1992-10-28', '1992-10-28');


-- users authorities
-- ___________________

INSERT INTO pizza.jt_user_authorities(
	fk_user, fk_authority)
	VALUES ('jean', 'ROLE_USER');


-- ingredients
-- ___________________ 

INSERT INTO pizza.ingredients(
    id, generic_name, stock_quantity, price_for_composition)
    VALUES (1, 'emmental', 10, 1);

INSERT INTO pizza.ingredients(
    id, generic_name, stock_quantity, price_for_composition)
    VALUES (2, 'poivron', 20, 2);

INSERT INTO pizza.ingredients(
    id, generic_name, stock_quantity, price_for_composition)
    VALUES (3, 'oignons', 30, 3);

INSERT INTO pizza.ingredients(
    id, generic_name, stock_quantity, price_for_composition)
    VALUES (4, 'olives', 40, 4);

INSERT INTO pizza.ingredients(
    id, generic_name, stock_quantity, price_for_composition)
    VALUES (5, 'mozzarella', 50, 5);

INSERT INTO pizza.ingredients(
    id, generic_name, stock_quantity, price_for_composition)
    VALUES (6, 'bacon', 60, 6);

INSERT INTO pizza.ingredients(
    id, generic_name, stock_quantity, price_for_composition)
    VALUES (7, 'aubergines', 70, 7);

INSERT INTO pizza.ingredients(
    id, generic_name, stock_quantity, price_for_composition)
    VALUES (8, 'bleu_d_auvergne', 80, 8);

INSERT INTO pizza.ingredients(
    id, generic_name, stock_quantity, price_for_composition)
    VALUES (9, 'jambon', 90, 9);

INSERT INTO pizza.ingredients(
    id, generic_name, stock_quantity, price_for_composition)
    VALUES (10, 'tomate', 100, 10);

INSERT INTO pizza.ingredients(
    id, generic_name, stock_quantity, price_for_composition)
    VALUES (11, 'champignon', 110, 11);

INSERT INTO pizza.ingredients(
    id, generic_name, stock_quantity, price_for_composition)
    VALUES (12, 'fromage_de_chevre', 120, 12);
	
INSERT INTO pizza.ingredients(
    id, generic_name, stock_quantity, price_for_composition)
    VALUES (13, 'patte', 100, 12);

SELECT setval('pizza.ingredients_id_seq', 13, true);


-- categories of pizzas
-- ___________________

INSERT INTO pizza.pizza_category(
    category)
    VALUES ('vegan');

INSERT INTO pizza.pizza_category(
    category)
    VALUES ('cannibale');

INSERT INTO pizza.pizza_category(
    category)
    VALUES ('spicy');

INSERT INTO pizza.pizza_category(
    category)
    VALUES ('special');

INSERT INTO pizza.pizza_category(
    category)
    VALUES ('custom');



-- standard pizzas
-- ___________________

INSERT INTO pizza.pizzas(
    id, generic_name, price)
    VALUES (1, 'margherita', 10);

INSERT INTO pizza.pizzas(
    id, generic_name, price)
    VALUES (2, 'regina', 20);

INSERT INTO pizza.pizzas(
    id, generic_name, price)
    VALUES (3, 'fourCheese', 30);

INSERT INTO pizza.pizzas(
    id, generic_name, price)
    VALUES (4, 'veggie', 40);

-- custom pizzas 
-- ___________________ 

INSERT INTO pizza.pizzas(
    id, generic_name, price)
    VALUES (5,'CustomPizza', 12);

INSERT INTO pizza.pizzas(
    id, generic_name, price)
    VALUES (6, 'CustomPizza', 14);

SELECT setval('pizza.pizzas_id_seq', 6, true);


-- pizzas category
-- ___________________

insert INTO pizza.jt_pizza_category(
    fk_pizza, fk_category)
    VALUES (1, 'spicy');

insert INTO pizza.jt_pizza_category(
    fk_pizza, fk_category)
    VALUES (4, 'vegan');

insert INTO pizza.jt_pizza_category(
    fk_pizza, fk_category)
    VALUES (3, 'special');

insert INTO pizza.jt_pizza_category(
    fk_pizza, fk_category)
    VALUES (2, 'cannibale');

-- standard pizzas ingredients
-- ___________________

INSERT INTO pizza.recipes(
    fk_ingredient, fk_pizza)
    VALUES (5, 1);
INSERT INTO pizza.recipes(
    fk_ingredient, fk_pizza)
    VALUES (10, 1);
INSERT INTO pizza.recipes(
    fk_ingredient, fk_pizza)
    VALUES (13, 1);


INSERT INTO pizza.recipes(
    fk_ingredient, fk_pizza,quantity)
    VALUES (5, 2, 2);
INSERT INTO pizza.recipes(
    fk_ingredient, fk_pizza,quantity)
    VALUES (9, 2, 1);
INSERT INTO pizza.recipes(
    fk_ingredient, fk_pizza,quantity)
    VALUES (10, 2,3);
INSERT INTO pizza.recipes(
    fk_ingredient, fk_pizza,quantity)
    VALUES (11, 2,2);
INSERT INTO pizza.recipes(
    fk_ingredient, fk_pizza)
    VALUES (13, 2);


INSERT INTO pizza.recipes(
    fk_ingredient, fk_pizza,quantity)
    VALUES (1, 3,5);
INSERT INTO pizza.recipes(
    fk_ingredient, fk_pizza,quantity)
    VALUES (5, 3,6);
INSERT INTO pizza.recipes(
    fk_ingredient, fk_pizza,quantity)
    VALUES (8, 3,7);
INSERT INTO pizza.recipes(
    fk_ingredient, fk_pizza,quantity)
    VALUES (10, 3,8);
INSERT INTO pizza.recipes(
    fk_ingredient, fk_pizza,quantity)
    VALUES (12, 3,10);
INSERT INTO pizza.recipes(
    fk_ingredient, fk_pizza)
    VALUES (13, 3);

INSERT INTO pizza.recipes(
    fk_ingredient, fk_pizza,quantity)
    VALUES (2, 4,11);
INSERT INTO pizza.recipes(
    fk_ingredient, fk_pizza,quantity)
    VALUES (3, 4,2);
INSERT INTO pizza.recipes(
    fk_ingredient, fk_pizza,quantity)
    VALUES (4, 4,3);
INSERT INTO pizza.recipes(
    fk_ingredient, fk_pizza,quantity)
    VALUES (7, 4,3);
INSERT INTO pizza.recipes(
    fk_ingredient, fk_pizza,quantity)
    VALUES (10, 4,2);
INSERT INTO pizza.recipes(
    fk_ingredient, fk_pizza,quantity)
    VALUES (11, 4,2);
INSERT INTO pizza.recipes(
    fk_ingredient, fk_pizza)
    VALUES (13, 4);



-- custom pizzas ingredients
-- ___________________

INSERT INTO pizza.recipes(
    fk_ingredient, fk_pizza,quantity)
    VALUES (2, 5,3);
INSERT INTO pizza.recipes(
    fk_ingredient, fk_pizza)
    VALUES (13, 5);

INSERT INTO pizza.recipes(
    fk_ingredient, fk_pizza,quantity)
    VALUES (1, 6,6);
INSERT INTO pizza.recipes(
    fk_ingredient, fk_pizza)
    VALUES (13, 6);

-- orders
-- ___________________

INSERT INTO pizza.orders(
    id, fk_user, fk_payment, creation_date, promo_name, reduction)
    VALUES (1 , 'jean', null, '2018-11-07', null, 0);

SELECT setval('pizza.orders_id_seq', 1, true);


-- order lines
-- ___________________ 

INSERT INTO pizza.order_line(
    fk_order, fk_pizza, quantity)
    VALUES (1, 1, 1);

INSERT INTO pizza.order_line(
    fk_order, fk_pizza, quantity)
    VALUES (1, 2, 2);

INSERT INTO pizza.order_line(
    fk_order, fk_pizza, quantity)
    VALUES (1, 3, 3);

INSERT INTO pizza.order_line(
    fk_order, fk_pizza, quantity)
    VALUES (1, 4, 4);

INSERT INTO pizza.order_line(
    fk_order, fk_pizza, quantity)
    VALUES (1, 5, 5);
	
	
-- user pizzas favorites
-- ___________________

INSERT INTO pizza.jt_user_pizzas(
    fk_user, fk_pizza)
    VALUES ('jean', 1);
INSERT INTO pizza.jt_user_pizzas(
    fk_user, fk_pizza)
    VALUES ('jean', 2);
INSERT INTO pizza.jt_user_pizzas(
    fk_user, fk_pizza)
    VALUES ('jean', 3);	
	
	
	
	