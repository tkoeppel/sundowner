-- Create Tablespace
--- mkdir '/var/lib/postgresql/sundowner_dev/'

CREATE TABLESPACE sundowner_dev_tablespace OWNER sundowner_dev LOCATION '/var/lib/postgresql/sundowner_dev';
COMMENT ON TABLESPACE sundowner_dev_tablespace IS 'Sundowner DEV DB Tablespace';                                                        
GRANT CREATE ON TABLESPACE sundowner_dev_tablespace TO public;
