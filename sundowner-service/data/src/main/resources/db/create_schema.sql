-- Create Schema
CREATE SCHEMA sundowner_%s_schema AUTHORIZATION sundowner_%s;
COMMENT ON SCHEMA sundowner_%s_schema IS 'Sundowner %s Schema';
GRANT EXECUTE ON ALL FUNCTIONS IN SCHEMA sundowner_%s_schema TO sundowner_%s;
GRANT SELECT, INSERT, UPDATE, DELETE, TRUNCATE, TRIGGER ON ALL TABLES IN SCHEMA sundowner_%s_schema TO sundowner_%s;
GRANT USAGE, CREATE ON SCHEMA sundowner_%s_schema TO sundowner_%s;
