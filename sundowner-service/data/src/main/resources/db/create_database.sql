-- Create Database
CREATE DATABASE sundowner_%s_db;
COMMENT ON DATABASE sundowner_%s_db IS 'Sundowner %s Database';

ALTER DATABASE sundowner_%s_db OWNER TO sundowner_%s;

GRANT EXECUTE ON ALL FUNCTIONS IN SCHEMA public TO sundowner_%s;
GRANT SELECT, INSERT, UPDATE, DELETE, TRUNCATE, TRIGGER ON ALL TABLES IN SCHEMA public TO sundowner_%s;
GRANT USAGE, CREATE ON SCHEMA public TO sundowner_%s;