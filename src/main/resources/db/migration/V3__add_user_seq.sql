CREATE SEQUENCE IF NOT EXISTS user_id_seq AS bigint START 1 increment 1;

ALTER TABLE users ALTER COLUMN id SET DEFAULT nextval('user_id_seq');

ALTER SEQUENCE user_id_seq OWNED BY users.id;

