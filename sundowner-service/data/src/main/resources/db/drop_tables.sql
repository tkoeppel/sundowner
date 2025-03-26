DO $$
DECLARE
    rec record;
BEGIN

    -- Drop extensions first
    EXECUTE 'DROP EXTENSION IF EXISTS postgis CASCADE';
    EXECUTE 'DROP EXTENSION IF EXISTS postgis_topology CASCADE';

    FOR rec IN
        SELECT table_name
        FROM information_schema.tables
        WHERE table_schema = 'public'
        ORDER BY table_name DESC
    LOOP
        EXECUTE 'DROP TABLE IF EXISTS ' || quote_ident(rec.table_name) || ' CASCADE';
    END LOOP;

END $$;