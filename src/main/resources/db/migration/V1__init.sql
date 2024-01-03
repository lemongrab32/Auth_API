CREATE TABLE IF NOT EXISTS users
(
    ID BIGINT,
    LOGIN CHARACTER VARYING(30),
    PASSWORD CHARACTER VARYING(100),
    MAIL CHARACTER VARYING(50),
    PRIMARY KEY(ID)
);

CREATE TABLE IF NOT EXISTS roles
(

    ID BIGINT,
    NAME varchar(30),
    PRIMARY KEY(Id)
);

CREATE TABLE IF NOT EXISTS roles_x_users
(
    user_id bigint not null,
    role_id bigint not null,
    PRIMARY KEY(user_id, role_id),
    FOREIGN KEY(user_id) references users(id),
    FOREIGN KEY(role_id) references roles(id)
);


