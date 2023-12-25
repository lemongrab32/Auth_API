CREATE TABLE IF NOT EXISTS users
(
    Id BIGINT,
    LOGIN CHARACTER VARYING(30),
    PASSWORD CHARACTER VARYING(15),
    MAIL CHARACTER VARYING(15),
    ROLE CHARACTER VARYING(30),
    PRIMARY KEY(Id)
);

CREATE TABLE IF NOT EXISTS roles
(

    Id BIGINT,
    name varchar(30),
    PRIMARY KEY(Id)
);

CREATE TABLE IF NOT EXISTS roles_x_users
(
    user_id bigint not null,
    role_id bigint not null,
    primary key(user_id, role_id),
    foreign key(user_id) references users(id),
    foreign key(role_id) references roles(id)
);


