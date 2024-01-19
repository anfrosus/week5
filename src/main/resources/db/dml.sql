insert into users(user_balance, user_name, user_password, user_role)
values(10000, "userName1", "pwd", "USER");

insert into users(user_balance, user_name, user_password, user_role)
values(11000, "userName2", "pwd", "USER");

insert into users(user_balance, user_name, user_password, user_role)
values(12000, "userName3", "pwd", "USER");

insert into users(user_balance, user_name, user_password, user_role)
values(13000, "userName4", "pwd", "USER");

insert into store(store_account, store_revenue, user_id)
values (100, 100, 1);

insert into product(product_price, product_stock_left, store_id, product_category, product_name, product_description)
values (2000, 20, 1, '카테고리1', '상품이름1', '상품 설명1');

-- insert into product(product_price, product_stock_left, store_id, product_category, product_name, product_description)
-- values (3000, 20, 2, "카테고리2", "상품이름2", "상품 설명2")
--
-- insert into product(product_price, product_stock_left, store_id, product_category, product_name, product_description)
-- values (4000, 20, 3, "카테고리3", "상품이름3", "상품 설명3")
--
-- insert into product(product_price, product_stock_left, store_id, product_category, product_name, product_description)
-- values (5000, 20, 4, "카테고리4", "상품이름4", "상품 설명4")

insert into orders(total_price, user_id, order_status)
values (6000, 1, 'PAYMENT_WAITING');

insert into order_detail(order_id, product_id, quantity)
values (1, 1, 3);

insert into orders(total_price, user_id, order_status)
values (6000, 2, 'PAYMENT_WAITING');

insert into order_detail(order_id, product_id, quantity)
values (2, 1, 3);

insert into orders(total_price, user_id, order_status)
values (6000, 3, 'PAYMENT_WAITING');

insert into order_detail(order_id, product_id, quantity)
values (3, 1, 3);

insert into orders(total_price, user_id, order_status)
values (6000, 4, 'PAYMENT_WAITING');

insert into order_detail(order_id, product_id, quantity)
values (4, 1, 3);