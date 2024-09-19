DO
$$
DECLARE
    rec record;
BEGIN
    FOR rec IN
        SELECT *
        FROM pg_Tables
        WHERE tablename LIKE 'sundowner_dev%'
        ORDER BY tablename
    LOOP
        EXECUTE 'DROP TABLE IF EXISTS '|| rec.tablename ||' CASCADE';
    END LOOP;
END
$$