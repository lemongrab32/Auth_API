CREATE SEQUENCE role_id_seq AS bigint START 1 increment 1;

ALTER TABLE roles ALTER COLUMN id SET DEFAULT nextval('role_id_seq');

ALTER SEQUENCE role_id_seq OWNED BY roles.id;

INSERT INTO roles (name) values ('ROLE_ADMIN'), ('ROLE_USER');