CREATE PROCEDURE assign_demo(INOUT _val text DEFAULT null)
    LANGUAGE plpgsql AS
$proc$
BEGIN
SELECT crypto_seed_cursor FROM "crypto" WHERE id = 1
    INTO _val;                              -- !!!
END
$proc$;