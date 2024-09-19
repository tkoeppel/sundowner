-- Create Schema
CREATE SCHEMA sundowner_dev_schema AUTHORIZATION sundowner_dev;
COMMENT ON SCHEMA sundowner_dev_schema IS 'Sundowner Dev Schema';
GRANT EXECUTE ON ALL FUNCTIONS IN SCHEMA sundowner_dev_schema TO sundowner_dev;
GRANT SELECT, INSERT, UPDATE, DELETE, TRUNCATE, TRIGGER ON ALL TABLES IN SCHEMA sundowner_dev_schema TO sundowner_dev;
GRANT USAGE, CREATE ON SCHEMA sundowner_dev_schema TO sundowner_dev;