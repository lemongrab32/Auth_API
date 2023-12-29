INSERT INTO users(id, login, password, mail) VALUES
                                                 (1, 'Pablo', 2281337, 'dik@mail.ru');
INSERT INTO roles(id, name) VALUES
                                (1, 'ROLE_ADMIN'),
                                (2, 'ROLE_USER'),
                                (3, 'ROLE_MODERATOR');

INSERT INTO roles_x_users(user_id, role_id) VALUES
                                                (1, 1),
                                                (1, 2),
                                                (1, 3);

