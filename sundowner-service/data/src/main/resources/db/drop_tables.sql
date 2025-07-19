DO $$
DECLARE
    rec record;
BEGIN

    FOR rec IN
        SELECT table_name
        FROM information_schema.tables
        WHERE table_schema = 'public'
        ORDER BY table_name DESC
    LOOP
        EXECUTE 'DROP TABLE IF EXISTS ' || quote_ident(rec.table_name) || ' CASCADE';
    END LOOP;

END $$;