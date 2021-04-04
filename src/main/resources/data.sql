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

INSERT INTO USERS(NAME, EMAIL, PASSWORD, DATE, LAST_VOTE)
VALUES ('User', 'user@gmail.com', 'password', '2021-02-20', '2021-03-30'),
       ('Admin', 'admin@gmail.com', 'password', '2021-02-20', '2021-03-30'),
       ('User1', 'user@yahoo.com', 'password', '2021-02-20', '2021-03-30'),
       ('User2', 'user@facebook.com', 'password', '2021-02-20', '2021-03-30');

INSERT INTO USER_ROLES(USER_ID, ROLE)
VALUES (10000, 'USER'),
       (10001, 'ADMIN'),
       (10002, 'USER'),
       (10003, 'USER');

INSERT INTO RESTAURANTS(NAME, CONTACTS, DATE)
VALUES ('BreakingBad', 'Los Angeles, USA', '2015-02-20'),
       ('LuckyPub', 'Odessa, Ukraine', '2021-02-20'),
       ('Consuela', 'Paris, France', '2010-02-21'),
       ('No Time To Die', 'Amsterdam, Netherlands', '2021-03-15'),
       ('Drink Milk', 'Kyiv, Ukraine', '2014-10-12');

INSERT INTO DISHES(REST_ID, NAME, PRICE, DATE)
VALUES (10004, 'Pizza', 5, '2021-03-28'),
       (10004, 'Steak', 10, '2021-03-28'),
       (10004, 'Beer', 2, '2021-03-28'),
       (10005, 'Soup', 6, '2021-03-29'),
       (10005, 'Pizza', 6, '2021-03-29'),
       (10005, 'Aperol', 3, '2021-03-29'),
       (10006, 'Soup', 6, '2021-03-30'),
       (10006, 'Pizza', 6, '2021-03-30'),
       (10006, 'Beer', 3, '2021-03-30'),
       (10007, 'Soup', 6, '2021-03-27'),
       (10007, 'Pizza', 6, '2021-03-27'),
       (10007, 'Beer', 3, '2021-03-27'),
       (10008, 'Soup', 6, '2021-03-26'),
       (10008, 'Pizza', 6, '2021-03-26'),
       (10008, 'Pasta', 3, '2021-01-26');

INSERT INTO VOTES(REST_ID)
VALUES (10004),
       (10005),
       (10006),
       (10007),
       (10008);

