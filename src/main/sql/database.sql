
DROP TABLE IF EXISTS "user";
CREATE TABLE "user" (
    user_id serial PRIMARY KEY,
    user_email varchar(255),
    user_pseudo varchar(255),
    user_password varchar(255)
);

DROP TABLE IF EXISTS "crypto";
CREATE TABLE "crypto" (
    crypto_id serial PRIMARY KEY,
    crypto_name varchar(255),
    crypto_slug varchar(255),
    crypto_desc text
);