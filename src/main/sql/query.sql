

-- Il s'agit d'un exemple de requete SQL pour la récupération des valeurs de chaque Crypto
-- Utilisé uniquement lors de l'initialisation de l'Objet Crypto
-- Les prochaines valeurs sont automatiquement mis à jour gràce à un listener
SELECT crypto_id, crypto_name, crypto_slug, crypto_desc, crypto_seed, price_value as crypto_price
FROM "crypto"
         INNER JOIN (
    SELECT price_crypto, max(price_date) as MaxDate FROM price group by price_crypto
) tm ON crypto_id = tm.price_crypto
         INNER JOIN price ON crypto_id = price.price_crypto;
