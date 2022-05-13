
DROP TABLE IF EXISTS "user";
CREATE TABLE "user" (
    user_id serial PRIMARY KEY,
    user_email varchar(255),
    user_pseudo varchar(255),
    user_password varchar(255)
);

-- la colonne seed permet d'associer un crypto à une série d'évolution de valeur
-- qu'on a enregistré dans le fichier stocks-random.txt
DROP TABLE IF EXISTS "crypto";
CREATE TABLE "crypto" (
    crypto_id serial PRIMARY KEY,
    crypto_name varchar(255),
    crypto_slug varchar(255),
    crypto_desc text,
    crypto_seed INT,
    crypto_seed_cursor INT
);

-- PERMET DE DÉFINIR LA VALEUR D'UN CRYPTO À UN INSTANT DONNÉ
-- Il s'agit d'une association journalisée
-- À chaque instant donné du calendrier correspond
-- à un enregistrement
DROP TABLE IF EXISTS "price";
CREATE TABLE "price" (
    price_crypto INT,
    price_date DATE NOT NULL,
    price_value INT,
    CONSTRAINT fk_crypto FOREIGN KEY (price_crypto) REFERENCES crypto(crypto_id)
);