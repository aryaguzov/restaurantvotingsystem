DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS votes;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS dishes;
DROP TABLE IF EXISTS menus;
DROP TABLE IF EXISTS restaurants;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 10000;

CREATE TABLE users
(
    id       INT DEFAULT nextval('global_seq') PRIMARY KEY,
    name     VARCHAR NOT NULL,
    email    VARCHAR NOT NULL,
    password VARCHAR NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_roles
(
    user_id INT NOT NULL,
    role    VARCHAR,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE restaurants
(
    id       INT DEFAULT nextval('global_seq') PRIMARY KEY,
    name     VARCHAR NOT NULL,
    contacts VARCHAR NOT NULL
);
CREATE UNIQUE INDEX restaurants_unique_name_idx ON restaurants (name);

CREATE TABLE menus
(
    id      INT       DEFAULT nextval('global_seq') PRIMARY KEY,
    rest_id INT NOT NULL,
    date    TIMESTAMP DEFAULT now(),
    FOREIGN KEY (rest_id) REFERENCES restaurants (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX menus_rest_date_idx ON menus (rest_id, date);

CREATE TABLE dishes
(
    id      INT DEFAULT nextval('global_seq') PRIMARY KEY,
    menu_id INT     NOT NULL,
    name    VARCHAR NOT NULL,
    price   INT     NOT NULL,
    FOREIGN KEY (menu_id) REFERENCES menus (id) ON DELETE CASCADE
);

CREATE TABLE votes
(
    id      INT       DEFAULT nextval('global_seq') PRIMARY KEY,
    user_id INT                     NOT NULL,
    rest_id INT                     NOT NULL,
    date    TIMESTAMP DEFAULT now() NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (rest_id) REFERENCES restaurants (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX votes_unique_user_date_idx ON votes (user_id, date);