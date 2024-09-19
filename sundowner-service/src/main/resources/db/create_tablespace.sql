-- Create Tablespace
mkdir '/var/lib/postgresql/sundowner_%s/'

CREATE TABLESPACE sundowner_%s_tablespace OWNER sundowner_%s LOCATION '/var/lib/postgresql/sundowner_%s';
COMMENT ON TABLESPACE sundowner_%s_tablespace IS 'Sundowner %s DB Tablespace';                                                        
GRANT CREATE ON TABLESPACE sundowner_%s_tablespace TO public;
