Alter TABLE users
Alter COLUMN mail TYPE varchar(40),
Alter COLUMN password TYPE varchar(50);

INSERT INTO users(id, login, password, mail) VALUES (1, 'Pablo Escobaro', 2281337, 'dik@mail.ru'), (2, 'Garguv Ich', 3123123, 'superproger123@mail.ru');

INSERT INTO roles(id, name) VALUES (1, 'ROLE_ADMIN'), (2, 'ROLE_USER');

INSERT INTO roles_x_users VALUES (1, 2), (2, 1);
