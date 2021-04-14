DELETE
FROM USER_ROLES;
DELETE
FROM VOTES;
DELETE
FROM DISHES;
DELETE
FROM RESTAURANTS;
DELETE
FROM USERS;
ALTER SEQUENCE GLOBAL_SEQ RESTART WITH 10000;

INSERT INTO USERS(NAME, EMAIL, PASSWORD)
VALUES ('User', 'user@gmail.com', 'password'),
       ('Admin', 'admin@gmail.com', 'password'),
       ('User1', 'user@yahoo.com', 'password'),
       ('User2', 'user@facebook.com', 'password');

INSERT INTO USER_ROLES(USER_ID, ROLE)
VALUES (10000, 'USER'),
       (10001, 'ADMIN'),
       (10002, 'USER'),
       (10003, 'USER');

INSERT INTO RESTAURANTS(NAME, CONTACTS)
VALUES ('BreakingBad', 'Los Angeles, USA'),
       ('LuckyPub', 'Odessa, Ukraine'),
       ('Consuela', 'Paris, France'),
       ('No Time To Die', 'Amsterdam, Netherlands'),
       ('Drink Milk', 'Kyiv, Ukraine');

INSERT INTO MENUS(REST_ID, DATE)
VALUES (10004, '2021-03-24'),
       (10005, '2021-03-25'),
       (10006, '2021-03-26'),
       (10007, '2021-03-27'),
       (10008, '2021-03-28');

INSERT INTO DISHES(MENU_ID, NAME, PRICE)
VALUES (10009, 'Pizza', 5),
       (10009, 'Steak', 10),
       (10009, 'Beer', 2),
       (10010, 'Soup', 6),
       (10010, 'Pizza', 6),
       (10010, 'Aperol', 3),
       (10011, 'Soup', 6),
       (10011, 'Pizza', 6),
       (10011, 'Beer', 3),
       (10012, 'Soup', 6),
       (10012, 'Pizza', 6),
       (10012, 'Beer', 3),
       (10012, 'Soup', 6),
       (10013, 'Pizza', 6),
       (10013, 'Pasta', 3);

INSERT INTO VOTES(USER_ID, REST_ID, DATE)
VALUES (10000, 10004, '2021-03-25'),
       (10001, 10005, '2021-03-26'),
       (10002, 10006, '2021-03-27'),
       (10003, 10007, '2021-03-28');

